# SWE431 Course Registration System

A Java 17 course registration project for SWE431 Software Testing and Validation. The `RegistrationSystem` API coordinates input validation, eligibility, enrollment, withdrawal, and waitlist promotion. This is a Maven library and test project; there is no standalone application to launch.

## Setup and verification

Use **JDK 17** for the same Java version the project targets. JDK 21 or 25 can also build the project; JaCoCo 0.8.14 officially supports class files through Java 25. Install Maven and Git, then check that `mvn -version` reports the intended JDK.

```sh
java -version
mvn -version
git --version
git clone https://github.com/FlandSako55/SWE431-Software-Testing-and-Validation-JUnit-5-Group-Project.git
cd SWE431-Software-Testing-and-Validation-JUnit-5-Group-Project
mvn test
```

Look for `BUILD SUCCESS` and the test summary. In IntelliJ IDEA, open `pom.xml` as a project, select the intended JDK for the project and Maven runner, then run **Maven → Lifecycle → test**. IntelliJ's bundled Maven works as well.

To run only the validation tests:

```sh
mvn "-Dtest=ValidationServiceTest" test
```

JUnit 5 results are written to `target/surefire-reports/`. Open `target/site/jacoco/index.html` and select the relevant class to inspect line and branch coverage. Green lines ran; red lines did not. A yellow branch marker means some outcomes were not exercised. For a fresh full-suite coverage report, use `mvn clean test`. Do not commit `target/`.

## Team responsibilities

| Member | Branch | Service | Testing and deliverables |
|---|---|---|---|
| 1 | `feat/validation` | `ValidationService` | Parameterized tests, this README |
| 2 | `feat/eligibility` | `EligibilityService` | [Decision table](docs/decision-table.md), [test design](docs/test-design.md) |
| 3 | `feat/enrollment` | `EnrollmentService` | Exception assertions, final JaCoCo report (`docs/coverage/`, pending) |
| 4 | `feat/withdrawal` | `WithdrawalService` | [Regression report](docs/regression.md), [contribution record](docs/contribution-record.md) |
| 5 (lead) | `feat/waitlist` | `WaitlistService` | Control-flow graph and basis paths (`docs/cfg-basis-paths.md`, pending), `RegistrationSystemTest` |

Eligibility and withdrawal were merged before validation. A green test run while enrollment or waitlist is unfinished does not verify the full registration flow. For the remaining work, merge validation, then enrollment, then waitlist. Verify the end-to-end tests and publish the final coverage report after all service changes are on `main`.

Services are in `src/main/java/com/registration/service/`; corresponding JUnit tests are in `src/test/java/com/registration/`.

### 1. Validation

| Method | Accepted format | Examples |
|---|---|---|
| `isValidStudentId` | Exactly nine ASCII digits (`[0-9]{9}`) | Accepts `202412345` and `000000001`; rejects 8 or 10 digits |
| `isValidCourseCode` | Two to four uppercase ASCII letters followed by exactly three ASCII digits (`[A-Z]{2,4}[0-9]{3}`) | Accepts `CS101`, `SWE431`, and `MATH201`; rejects `swe431` and `SWE4310` |

Both methods match the entire input and return `false` for `null`, empty strings, spaces, or characters outside the required ASCII sets. They do not trim, normalize, or look up student or course records. `register` rejects invalid formats before it checks eligibility or capacity. A null `Student` or `Course` object is a separate error and causes `IllegalArgumentException`.

`ValidationServiceTest` uses equivalence partitioning to select valid and invalid input classes and boundary value analysis for the ID length (8/9/10), course prefix length (1/2/4/5), and course suffix length (2/3/4). Each `@CsvSource` row contains an input and its expected result and runs independently; `@NullAndEmptySource` supplies null and empty inputs.

### 2. Eligibility and prerequisites

- `isEligible` requires an active student, all prerequisites completed, and `currentCredits + course.getCredits() <= student.getMaxCredits()`. Test equality at the credit limit and rejection just above it.
- `hasMetPrerequisites` checks `getCompletedCourses()`, not current enrollments. No prerequisites means this check passes. Both methods reject null arguments with `IllegalArgumentException`; `isEligible` also rejects negative current credits.
- The enrollment decision table includes validation, activity, prerequisites, credit, space, and duplicate status, with outcomes `ENROLLED`, `WAITLISTED`, or `REJECTED`. Associate each rule with a test. Gather each member's techniques and test list for the shared test design.

### 3. Enrollment and capacity

- `hasCapacity` compares the enrollment count with the course capacity. Test `max - 1`, `max`, `max + 1`, and zero capacity.
- Successful `enroll` updates both the course roster and the student's enrolled courses, then returns `ENROLLED`. A full course or a student already enrolled or waitlisted returns `REJECTED` without changing state. Assert `IllegalArgumentException` for all null argument cases.
- After integration, run the full suite on `main`. Copy the generated JaCoCo site to `docs/coverage/` and summarize each class's line and branch coverage in `docs/coverage/README.md`.

### 4. Withdrawal and regression

- Find and remove the roster entry by student ID, even if the supplied `Student` is a different object. Update the student's course set on success and return `true`. Return `false` without changes if not enrolled; throw `IllegalArgumentException` on null arguments. Withdrawal itself does not promote anyone.
- Preserve the regression evidence in separate commits: passing baseline, deliberate defect with a failing test, and repair with the test passing again. Record outputs and commit IDs. Use **Create a merge commit** for PRs containing that evidence, not squash. Check PR and commit references in the contribution record against GitHub before submission.

### 5. Waitlist, basis paths, and integration

- `addToWaitlist` returns `WAITLISTED` only when the course is full and the student is not already enrolled or queued. It does not add the course to the student's enrolled courses. Other valid-object requests return `REJECTED` without changes.
- `promoteNextStudent` returns `null` without touching the queue if no seat is available. Otherwise, consume the queue in FIFO order, discarding ineligible or already-enrolled candidates. Enroll and return the first eligible candidate, updating both records, or return `null` if there is no eligible candidate. Test the supplied eligibility predicate independently of `EligibilityService`.
- Draw the actual control-flow graph for `promoteNextStudent`, calculate cyclomatic complexity (`V(G) = E - N + 2`), and write one test per independent basis path. After the services are merged, test registration, waitlisting, withdrawal, promotion, and rejection of invalid input through `RegistrationSystem`.

## Shared rules and pull requests

- Check enrollment and waitlist membership by student ID with `Course.isEnrolled` and `Course.isWaitlisted`; `Student` does not override `equals`.
- A `REJECTED` result leaves the roster, queue, and student's enrollments unchanged. Assert these outcomes where applicable. Validation methods work on strings and do not change state.
- Follow each method's Javadoc. Coordinate before changing shared model classes, `RegistrationSystem`, or `pom.xml`.
- Write at least **five behavior test methods per member** and at least **20 tests project-wide**. The scaffold's `facadeProvidesService` test does not count toward a member's five. Use descriptive test names, assert return values and state changes where applicable, and use `assertThrows` for documented exceptions. See `CourseTest` for fixture and assertion examples.

Start a new feature branch from an up-to-date `main`; if the feature branch already exists, switch to it instead of recreating it.

```sh
git switch main
git pull --ff-only
git switch -c feat/validation
```

For an existing feature branch that needs updates, fetch `origin` and merge `origin/main` into the feature branch. If there is a conflict, resolve it in the affected files, stage those files, and commit the resolution. Coordinate with the file owner if the correct resolution is unclear.

Before opening a PR:

- [ ] The service has no remaining `TODO` implementations or placeholder exceptions.
- [ ] `mvn test` succeeds; relevant null, boundary, and no-state-change cases are covered.
- [ ] JaCoCo shows at least **80% line coverage** for your service; review any missed branches.
- [ ] Only your assigned service, tests, and deliverables changed. Use `git status` and stage specific files, not `git add .`.
- [ ] The PR description lists the behavior tested, test results, and associated deliverables.

Push only the feature branch (for example, `git push -u origin feat/validation`) and open a PR with `main` as its base. Push follow-up commits to the same branch to update the PR. Do not push directly to `main`; use the team's review process and squash ordinary feature PRs after approval, except when individual regression commits must be preserved.
