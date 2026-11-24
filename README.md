# Course Registration System

A Java 17 Maven skeleton for a small course registration system, prepared for
five team members to implement and test the services independently.

## Team areas

- Member 1: `ValidationService` — student ID and course code validation
- Member 2: `EligibilityService` — enrollment eligibility and prerequisites
- Member 3: `EnrollmentService` — course capacity and duplicate enrollment
- Member 4: `WithdrawalService` — course withdrawal
- Member 5: `WaitlistService` — waitlisting and promotion

Shared domain models are in `com.registration.model`, and
`RegistrationSystem` provides access to all five services.

## Maven commands

Run the JUnit 5 test suite:

```sh
mvn test
```

After running tests, view the JaCoCo coverage report at
`target/site/jacoco/index.html`.
