# Enrollment Decision Table

This document shows how **Decision Table Testing** is used to verify course registration (`RegistrationSystem.register`) and student eligibility (`EligibilityService.isEligible`).

**Legend:** `Y` = Yes (True) · `N` = No (False) · `–` = Don't Care (Does not change the result)

---

## 1. Full Registration Decision Table (`RegistrationSystem.register`)

When a student tries to register for a course (with non-null inputs and `currentCredits >= 0`), seven rules decide whether the result is `ENROLLED`, `WAITLISTED`, or `REJECTED`. When the result is `REJECTED`, neither the course lists nor the student's courses change.

| Condition / Rule | R1 | R2 | R3 | R4 | R5 | R6 | R7 | R8 |
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **C1.** Valid student ID (9 ASCII digits) | N | Y | Y | Y | Y | Y | Y | Y |
| **C2.** Valid course code (2–4 letters + 3 digits) | – | N | Y | Y | Y | Y | Y | Y |
| **C3.** Student is active (`isActive()`) | – | – | N | Y | Y | Y | Y | Y |
| **C4.** Prerequisites completed (`hasMetPrerequisites`) | – | – | – | N | Y | Y | Y | Y |
| **C5.** Within credit limit (`current + course <= max`) | – | – | – | – | N | Y | Y | Y |
| **C6.** Free seat available (`enrolled < capacity`) | – | – | – | – | – | – | Y | N |
| **C7.** Not already enrolled or waitlisted | – | – | – | – | – | N | Y | Y |
| **Result** | **REJECTED** | **REJECTED** | **REJECTED** | **REJECTED** | **REJECTED** | **REJECTED** | **ENROLLED** | **WAITLISTED** |
| **State Change** | None | None | None | None | None | None | Enrolled | Waitlisted |

### Rule-to-Test Mapping

| Rule | Meaning | Test Class & Method |
|---|---|---|
| **R1** | Invalid student ID | `ValidationServiceTest` & `RegistrationSystemTest` |
| **R2** | Invalid course code | `ValidationServiceTest` & `RegistrationSystemTest` |
| **R3** | Inactive student | `EligibilityServiceTest#inactiveStudentIsIneligible` |
| **R4** | Missing or uncompleted prerequisite | `EligibilityServiceTest#missingPrerequisiteIsIneligible` |
| **R5** | Total credits exceed `maxCredits` | `EligibilityServiceTest#checksCreditLimitBoundaries` (`28 + 3 > 30`) |
| **R6** | Student is already enrolled or waitlisted | `EnrollmentServiceTest` & `WaitlistServiceTest` |
| **R7** | Valid, eligible student and free seat exists | `EligibilityServiceTest#eligibleWhenAllConditionsHold` & `EnrollmentServiceTest` |
| **R8** | Valid, eligible student and course is full | `WaitlistServiceTest` & `RegistrationSystemTest` |

---

## 2. Eligibility Unit Decision Table (`EligibilityService.isEligible`)

Inside `EligibilityService`, three boolean conditions control eligibility. Out of 8 (2³) total combinations, collapsing "Don't Care" (`–`) cases produces **4 core rules**:

| Condition / Rule | E1 (Eligible) | E2 (Inactive) | E3 (Missing Prereq) | E4 (Over Limit) |
|---|:---:|:---:|:---:|:---:|
| **C3.** Student is active (`isActive()`) | Y | N | Y | Y |
| **C4.** Prerequisites completed | Y | – (Y) | N | Y |
| **C5.** Within credit limit (`current + course <= max`) | Y | – (Y) | – (Y) | N |
| **Expected Result (`isEligible`)** | **`true`** | **`false`** | **`false`** | **`false`** |
| **Test Method** | `eligibleWhenAllConditionsHold`, `checksCreditLimitBoundaries` (`0`, `27`) | `inactiveStudentIsIneligible` | `missingPrerequisiteIsIneligible` | `checksCreditLimitBoundaries` (`28`) |

> **Why Decision Table Testing?**
> Course registration depends on several independent rules working together. A decision table ensures we test every rejection reason one by one (while keeping all other conditions `true`), proving that no single broken rule can accidentally pass.
