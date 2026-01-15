package com.hotelsmarttrack.billing;

import com.hotelsmarttrack.base.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

