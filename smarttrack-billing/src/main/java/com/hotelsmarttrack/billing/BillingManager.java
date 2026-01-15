package com.hotelsmarttrack.billing;

import com.hotelsmarttrack.base.entity.Invoice;
import com.hotelsmarttrack.base.entity.Payment;
import com.hotelsmarttrack.base.service.BillingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * BillingManager - Implementation of BillingService.
 * Business logic for Billing & Payment Management (Rule 2 & 3).
 */
@Service
public class BillingManager implements BillingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public BillingManager(InvoiceRepository invoiceRepository,
                          PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Invoice generateInvoice(Long stayId) {
        // Calculate charges (simplified)
        BigDecimal roomCharges = BigDecimal.valueOf(150.00); // Mock room charge
        BigDecimal incidentalCharges = BigDecimal.valueOf(50.00); // Mock incidental
        BigDecimal taxes = roomCharges.add(incidentalCharges).multiply(BigDecimal.valueOf(0.10));
        BigDecimal totalAmount = roomCharges.add(incidentalCharges).add(taxes);

        Invoice invoice = new Invoice();
        // ⚠️ invoiceId 不要自己 set，交给 @GeneratedValue
        invoice.setRoomCharges(roomCharges);
        invoice.setIncidentalCharges(incidentalCharges);
        invoice.setTaxes(taxes);
        invoice.setDiscounts(BigDecimal.ZERO);
        invoice.setTotalAmount(totalAmount);
        invoice.setAmountPaid(BigDecimal.ZERO);
        invoice.setOutstandingBalance(totalAmount);
        invoice.setStatus("Issued");
        invoice.setIssuedTime(LocalDateTime.now());
        invoice.setPayments(new ArrayList<>());

        // 关键：为了能通过 stayId 查到 invoice，你需要把 invoice.setStay(stay对象)
        // 但 BillingManager 目前拿不到 Stay 对象（只拿到 stayId）。
        // 所以先保存 invoice，本阶段先不绑定 stay（等后续跨组件/服务联动再做）。
        Invoice saved = invoiceRepository.save(invoice);

        System.out.println("[BillingManager] Generated invoice for stay " + stayId +
                " - Total: $" + totalAmount);
        return saved;
    }

    @Override
    public BigDecimal computeTotalCharges(Long stayId) {
        return getInvoiceByStay(stayId)
                .map(Invoice::getTotalAmount)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public Optional<Invoice> getInvoiceByStay(Long stayId) {
        // 只有当 invoice 真的绑定了 stay 才会查得到
        return invoiceRepository.findByStay_StayId(stayId);
    }

    @Override
    @Transactional
    public Payment processPayment(Long invoiceId, BigDecimal amount, String paymentMethod) {
        Payment payment = new Payment();
        // ⚠️ paymentId 不要自己 set，交给 @GeneratedValue
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("Completed");
        payment.setTransactionReference(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setPaymentTime(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // Update invoice
        invoiceRepository.findById(invoiceId).ifPresent(invoice -> {
            if (invoice.getPayments() == null) {
                invoice.setPayments(new ArrayList<>());
            }
            invoice.getPayments().add(savedPayment);

            BigDecimal newPaidAmount = invoice.getAmountPaid().add(amount);
            invoice.setAmountPaid(newPaidAmount);
            invoice.setOutstandingBalance(invoice.getTotalAmount().subtract(newPaidAmount));

            if (invoice.getOutstandingBalance().compareTo(BigDecimal.ZERO) <= 0) {
                invoice.setStatus("Paid");
            } else {
                invoice.setStatus("Partially Paid");
            }

            invoiceRepository.save(invoice);

            System.out.println("[BillingManager] Processed payment: $" + amount +
                    " via " + paymentMethod + " - Ref: " + savedPayment.getTransactionReference());
        });

        return savedPayment;
    }

    @Override
    public List<Payment> getPaymentsForInvoice(Long invoiceId) {
        return getInvoiceById(invoiceId)
                .map(Invoice::getPayments)
                .orElse(new ArrayList<>());
    }

    @Override
    public BigDecimal getOutstandingBalance(Long invoiceId) {
        return getInvoiceById(invoiceId)
                .map(Invoice::getOutstandingBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<Invoice> getUnpaidInvoices() {
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getOutstandingBalance() != null
                        && i.getOutstandingBalance().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> getInvoicesByGuest(Long guestId) {
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getGuest() != null
                        && i.getGuest().getGuestId() != null
                        && i.getGuest().getGuestId().equals(guestId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateInvoiceStatus(Long invoiceId, String status) {
        invoiceRepository.findById(invoiceId).ifPresent(invoice -> {
            invoice.setStatus(status);
            invoiceRepository.save(invoice);
            System.out.println("[BillingManager] Updated invoice " + invoiceId + " status to: " + status);
        });
    }

    @Override
    @Transactional
    public void applyDiscount(Long invoiceId, BigDecimal discountAmount, String reason) {
        invoiceRepository.findById(invoiceId).ifPresent(invoice -> {
            BigDecimal currentDiscount = invoice.getDiscounts() == null ? BigDecimal.ZERO : invoice.getDiscounts();
            invoice.setDiscounts(currentDiscount.add(discountAmount));

            BigDecimal newTotal = invoice.getRoomCharges()
                    .add(invoice.getIncidentalCharges())
                    .add(invoice.getTaxes())
                    .subtract(invoice.getDiscounts());

            invoice.setTotalAmount(newTotal);
            invoice.setOutstandingBalance(newTotal.subtract(invoice.getAmountPaid()));

            invoiceRepository.save(invoice);

            System.out.println("[BillingManager] Applied discount: $" + discountAmount + " - " + reason);
        });
    }
}

