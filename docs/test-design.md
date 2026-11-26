# Test Design Document — Course Registration System

## 1. Testing Strategy Overview

Each service in our Course Registration System solves a different type of problem, so we selected the testing technique that best catches bugs in each module:

| Member | Service | Testing Techniques Used | Why We Chose Them |
|---|---|---|---|
| **1** | `ValidationService` | Equivalence Partitioning (EP), Boundary Value Analysis (BVA), Parameterized Testing | ID and course code rules have strict length and character limits (e.g., 8, 9, 10 digits), which fit data-driven `@ParameterizedTest` tables cleanly. |
| **2** | `EligibilityService` | Decision Table Testing, Boundary Value Analysis (BVA), Equivalence Partitioning (EP), Exception Testing | Eligibility combines three boolean rules (`active`, `prerequisites`, `credit limit`) and a numerical limit (`<= maxCredits`) where off-by-one (`<` vs `<=`) bugs often happen. |
| **3** | `EnrollmentService` | Boundary Value Analysis (BVA), Exception Assertions, Duplicate & State Checks, JaCoCo Coverage | Enrollment depends on seat limits (`0`, `max - 1`, `max`, `max + 1`) and must never modify lists when a request is `REJECTED`. |
| **4** | `WithdrawalService` | Equivalence Partitioning (EP), ID-based Matching, Regression Testing | Dropping a course updates both `Course` and `Student` by `studentId`, making it ideal for demonstrating how a regression test catches a missing state update. |
| **5** | `WaitlistService` & `RegistrationSystem` | White-Box Control-Flow Graph (CFG) & Basis Path Testing, Integration Testing | `promoteNextStudent` uses a loop with multiple `if` checks (seat check, queue order, eligibility), so basis path testing guarantees every independent path is covered. |

---

## 2. Test Design by Module

### 2.1 Member 1 — `ValidationService` (`ValidationServiceTest`)

* **Techniques:** Equivalence Partitioning, Boundary Value Analysis, `@ParameterizedTest` (`@CsvSource`, `@NullAndEmptySource`).
* **Target Inputs:** Valid 9-digit ASCII student IDs vs. 8/10 digits, letters, spaces, `null`, and empty strings; valid course codes (`2–4` uppercase ASCII letters + `3` digits) vs. wrong lengths, lowercase/non-ASCII characters, `null`, and empty strings.

*(Test method names will be added when `feat/validation` is merged.)*

---

### 2.2 Member 2 — `EligibilityService` (`EligibilityServiceTest`)

* **Techniques:** Decision Table Testing (see [decision-table.md](decision-table.md)), Boundary Value Analysis (`-1`, `0`, `maxCredits`, `maxCredits + 1`), Equivalence Partitioning, and `assertThrows` exception checks.

#### Test Case List (5 Core Test Methods — 7 Invocations)

| ID | Test Method | Technique & Type | Inputs | Expected Result | Why We Chose This Test (Justification) |
|---|---|---|---|---|---|
| **EL-01** | `eligibleWhenAllConditionsHold` | Decision Table (E1) & EP — Valid | Active student (`max = 30`), completed `{SWE201, SWE301}` (`15` cr) + course with `0` prerequisites | `true` | Verifies normal approval when all three conditions hold, both with and without prerequisites. |
| **EL-02** | `inactiveStudentIsIneligible` | Decision Table (E2) — Invalid | Inactive student (`active = false`), prereqs met, `currentCredits = 12` | `false`; lists unchanged | Isolates `isActive()`: even with valid credits and prerequisites, an inactive student must be rejected. |
| **EL-03** | `missingPrerequisiteIsIneligible` | Decision Table (E3) & EP — Invalid | Completed `{SWE201}`, currently enrolled in `{SWE301}` (not finished) | `false` | Proves that taking a prerequisite right now (`enrolledCourses`) does not count as completing it (`completedCourses`). |
| **EL-04** | `checksCreditLimitBoundaries` | Decision Table (E1/E4), BVA & `@ParameterizedTest` | `currentCredits` = `0` (`3 <= 30`), `27` (`30 == 30`), `28` (`31 > 30`) | `true, true, false`; lists unchanged | Catches off-by-one bugs (`<` written instead of `<=`) right at `maxCredits = 30` and rejects `max + 1`. |
| **EL-05** | `throwsForNullOrNegativeInputs` | Exception & BVA (`-1`) — Invalid | `null` student, `null` course, `currentCredits = -1` | `IllegalArgumentException` | Protects both service methods from `null` references and negative credit values (`-1` just below `0`). |

---

### 2.3 Member 3 — `EnrollmentService` (`EnrollmentServiceTest`)

* **Techniques:** Boundary Value Analysis (`0`, `max - 1`, `max`, `max + 1`), Duplicate Check by `studentId`, Exception Assertions, State Check on `REJECTED`.

*(Test method names will be added when `feat/enrollment` is merged.)*

---

### 2.4 Member 4 — `WithdrawalService` (`WithdrawalServiceTest`)

* **Techniques:** Equivalence Partitioning, Removal by `studentId` (different object instance), Exception Assertions, Regression Testing (`docs/regression.md`).

*(Test method names will be added when `feat/withdrawal` is merged.)*

---

### 2.5 Member 5 — `WaitlistService` & `RegistrationSystem` (`WaitlistServiceTest`, `RegistrationSystemTest`)

* **Techniques:** Control-Flow Graph (CFG) & McCabe Basis Path Testing (`docs/cfg-basis-paths.md`), FIFO Queue & Duplicate Checks, End-to-End Integration Testing.

*(Test method names will be added when `feat/waitlist` is merged.)*
