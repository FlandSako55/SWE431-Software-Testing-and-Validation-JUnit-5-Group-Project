# Regression Testing — `WithdrawalService`

Regression testing checks that a change to working code does not break
behavior that used to work. To show that our tests really protect
`WithdrawalService.withdraw`, we put an intentional defect into the code,
ran the tests and saw them **fail**, then fixed the defect and saw them
**pass** again. Each step is a separate commit on `feat/withdrawal`.

Command used for every step:

```sh
mvn "-Dtest=WithdrawalServiceTest" test
```

| Step | Commit | Code state | Result |
|---|---|---|---|
| A | `2f147f9` | Correct implementation + 8 tests | PASS (8/8) |
| B1 | `5fbf96f` | Defect 1 added | FAIL (2 of 8) |
| C1 | `ac01264` | Defect 1 fixed | PASS (8/8) |
| B2 | `4a43707` | Defect 2 added | FAIL (1 of 8) |
| C2 | `931841a` | Defect 2 fixed | PASS (8/8) |

To repeat a step, check out its commit (for example `git checkout 5fbf96f`)
and run the command above. `git switch feat/withdrawal` goes back.

---

## Regression 1 — Course not removed from the student

- **Defect (commit `5fbf96f`):** the line
  `student.withdrawFromCourse(course.getCourseCode());` was deleted.
  The student was removed from the course list, but the course stayed in
  the student's `enrolledCourses`, so the two records no longer matched.
- **Tests that caught it:**
  - `withdrawRemovesCourseFromStudent` (line 48:
    `assertFalse(ali.getEnrolledCourses().contains("SWE431"))`)
  - `withdrawFindsStudentByIdNotByObject` (line 59:
    `assertTrue(sameId.getEnrolledCourses().isEmpty())`)
- **Fail output:**

```text
[ERROR] Tests run: 8, Failures: 2, Errors: 0, Skipped: 0 <<< FAILURE! -- in com.registration.WithdrawalServiceTest
[ERROR]   WithdrawalServiceTest.withdrawFindsStudentByIdNotByObject:59 expected: <true> but was: <false>
[ERROR]   WithdrawalServiceTest.withdrawRemovesCourseFromStudent:48 expected: <false> but was: <true>
[ERROR] Tests run: 8, Failures: 2, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

- **Fix (commit `ac01264`):** the line was added back.
- **Pass output:**

```text
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0 -- in com.registration.WithdrawalServiceTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Regression 2 — Removing the wrong object

- **Defect (commit `4a43707`):**
  `course.removeEnrolledStudent(enrolledStudent)` was changed to
  `course.removeEnrolledStudent(student)`. `Student` has no `equals`
  method, so `List.remove` only finds the exact same object. When the
  caller passes a different `Student` object with the same student ID,
  nothing is removed from the course, but the method still returns `true`.
- **Test that caught it:** `withdrawFindsStudentByIdNotByObject`
  (line 58: `assertFalse(course.isEnrolled("202412345"))`).
  The other 7 tests still passed, so this defect is only caught by the
  "same ID, different object" test.
- **Fail output:**

```text
[ERROR] Tests run: 8, Failures: 1, Errors: 0, Skipped: 0 <<< FAILURE! -- in com.registration.WithdrawalServiceTest
[ERROR]   WithdrawalServiceTest.withdrawFindsStudentByIdNotByObject:58 expected: <false> but was: <true>
[ERROR] Tests run: 8, Failures: 1, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

- **Fix (commit `931841a`):** the student found by ID in the course list is
  removed again.
- **Pass output:**

```text
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0 -- in com.registration.WithdrawalServiceTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

> **Note:** This branch must be merged with **Create a merge commit**, not
> Squash and merge. Squash would join these commits into one and the commit
> hashes above would no longer exist on `main`.
