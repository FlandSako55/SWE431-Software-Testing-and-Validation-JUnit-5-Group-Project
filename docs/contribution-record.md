# Contribution Record

This record lists what each member did, with their pull requests and commits.
It is checked against GitHub **Insights → Contributors** and the pull request
list, and is updated whenever a member's PR is merged.

Last updated: 2026-11-28

## Members and roles

| Member | Name (GitHub) | Role | Branch | Status |
|---|---|---|---|---|
| 1 | — | Validation + Parameterized Test + README | `feat/validation` | No PR yet |
| 2 | Samed Tevin (`samedTevin`) | Eligibility & Prerequisites + Decision Table + Test Design | `feat/eligibility` | Merged (#4) |
| 3 | — | Capacity & Duplicate + Exception Assertion + Coverage | `feat/enrollment` | No PR yet |
| 4 | Deniz Eren Gençtürk (`erengc`) | Course Withdrawal + Regression Test + Contribution Record | `feat/withdrawal` | PR open |
| 5 (leader) | — | Waitlist & Promotion + CFG & Basis Paths + Integration | `feat/waitlist` | No PR yet |

## Pull requests

| PR | Author | Title | Result |
|---|---|---|---|
| #1 | FlandSako55 | Add Java 17 course registration project skeleton | Closed without merge |
| #2 | FlandSako55 (Ali Yekta Dalkılıç) | Setup/skeleton | Merged 2026-11-24 |
| #3 | FlandSako55 (Ali Yekta Dalkılıç) | Readme biraz daha iyi oldu | Merged 2026-11-25 |
| #4 | samedTevin (Samed Tevin) | Implement EligibilityService, JUnit 5 tests, decision table, and test design | Merged 2026-11-26 |

## Work by member

### Project setup — Ali Yekta Dalkılıç (`FlandSako55`)

- Created the repository, the Maven project (`pom.xml`, JUnit 5, JaCoCo),
  the model classes (`Student`, `Course`, `EnrollmentStatus`),
  `RegistrationSystem`, the service skeletons and the example test
  `CourseTest` (PR #2, commit `37ff49e`).
- Wrote the README with the task split and the Git workflow
  (PR #3, commit `5f2ac16`).

### Member 2 — Samed Tevin (`samedTevin`)

- Implemented `EligibilityService` (`isEligible`, `hasMetPrerequisites`).
- Wrote `EligibilityServiceTest` (5 test methods, 7 runs with the
  parameterized test).
- Wrote `docs/decision-table.md` and `docs/test-design.md`.
- PR #4, commit `063f558`.

### Member 4 — Deniz Eren Gençtürk (`erengc`)

- Implemented `WithdrawalService.withdraw`: finds the enrolled student by
  student ID, removes them from the course and removes the course from the
  student.
- Wrote `WithdrawalServiceTest` (7 behavior tests + `facadeProvidesService`).
  JaCoCo: 100% line and 100% branch coverage for `WithdrawalService`.
- Ran two regression cycles (defect → FAIL → fix → PASS) and documented
  them in `docs/regression.md`.
- Wrote this contribution record.
- Branch `feat/withdrawal`, commits:

| Commit | Message |
|---|---|
| `2f147f9` | Implement WithdrawalService and JUnit 5 tests |
| `5fbf96f` | Regression 1: introduce defect (remove withdrawFromCourse call) |
| `ac01264` | Regression 1: fix defect (restore withdrawFromCourse call) |
| `4a43707` | Regression 2: introduce defect (remove parameter object instead of listed student) |
| `931841a` | Regression 2: fix defect (remove the student found by id) |

Members 1, 3 and 5 will be added when their pull requests are merged.
