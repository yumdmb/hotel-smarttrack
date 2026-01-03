# Hotel SmartTrack - Build Commands

All commands you need to build and run the project.

---

## Essential Commands

### Build Everything (First Time / After Changes)

```powershell
cd d:\Dev\hotel-smarttrack
mvn clean install -DskipTests
```

### Run the Application

```powershell
cd d:\Dev\hotel-smarttrack\smarttrack-application
mvn spring-boot:run
```

---

## Common Tasks

| Task                    | Command                            | Run From                  |
| ----------------------- | ---------------------------------- | ------------------------- |
| Full rebuild            | `mvn clean install -DskipTests`    | Project root              |
| Quick compile           | `mvn compile`                      | Project root              |
| Run app                 | `mvn spring-boot:run`              | `smarttrack-application/` |
| Run tests               | `mvn test`                         | Project root              |
| Package JARs            | `mvn package`                      | Project root              |
| Skip to specific module | `mvn install -pl smarttrack-guest` | Project root              |

---

## Verification Commands

### Check OSGi Manifest

```powershell
cd smarttrack-base-library\target
jar tf smarttrack-base-library-1.0-SNAPSHOT.jar | findstr MANIFEST
```

### View Manifest Contents

```powershell
jar xf smarttrack-base-library-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
type META-INF\MANIFEST.MF
```

---

## Build Lifecycle

1. **clean** - Delete `target/` folders
2. **compile** - Compile Java sources
3. **test** - Run unit tests
4. **package** - Create JAR files
5. **install** - Copy to local Maven repo (~/.m2)

> **Note**: Always use `install` when developing multi-module projects so modules can find each other.
