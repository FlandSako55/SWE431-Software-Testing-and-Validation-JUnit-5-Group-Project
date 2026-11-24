package com.registration;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;
import com.registration.service.EligibilityService;
import com.registration.service.EnrollmentService;
import com.registration.service.ValidationService;
import com.registration.service.WaitlistService;
import com.registration.service.WithdrawalService;

import java.util.function.ToIntFunction;

/** Entry point for registration, withdrawal, and waitlist promotion. */
public class RegistrationSystem {
    private final ValidationService validationService = new ValidationService();
    private final EligibilityService eligibilityService = new EligibilityService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final WithdrawalService withdrawalService = new WithdrawalService();
    private final WaitlistService waitlistService = new WaitlistService();

    /**
     * Validates the request and sends eligible students to enrollment or the waitlist.
     *
     * @param student student requesting enrollment
     * @param course requested course
     * @param currentCredits total credits already enrolled, before adding this course
     * @return the enrollment or waitlist outcome
     * @throws IllegalArgumentException if the student or course is null or credits are negative
     */
    public EnrollmentStatus register(Student student, Course course, int currentCredits) {
        if (student == null || course == null || currentCredits < 0) {
            throw new IllegalArgumentException("Student and course are required, and credits must be nonnegative");
        }
        if (!validationService.isValidStudentId(student.getStudentId())
                || !validationService.isValidCourseCode(course.getCourseCode())
                || !eligibilityService.isEligible(student, course, currentCredits)) {
            return EnrollmentStatus.REJECTED;
        }
        if (enrollmentService.hasCapacity(course)) {
            return enrollmentService.enroll(student, course);
        }
        return waitlistService.addToWaitlist(student, course);
    }

    /** Removes an enrollment and returns whether it existed. */
    public boolean withdraw(Student student, Course course) {
        return withdrawalService.withdraw(student, course);
    }

    /**
     * Offers a free seat to the first eligible student in the queue.
     *
     * @param course course with a seat to offer
     * @param currentCredits supplies each candidate's enrolled credit total
     * @return the promoted student, or {@code null} if no one was promoted
     * @throws IllegalArgumentException if the course or credit lookup is null
     */
    public Student promoteNextStudent(Course course, ToIntFunction<Student> currentCredits) {
        if (course == null || currentCredits == null) {
            throw new IllegalArgumentException("Course and credit lookup are required");
        }
        return waitlistService.promoteNextStudent(course,
                student -> eligibilityService.isEligible(student, course, currentCredits.applyAsInt(student)));
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public EligibilityService getEligibilityService() {
        return eligibilityService;
    }

    public EnrollmentService getEnrollmentService() {
        return enrollmentService;
    }

    public WithdrawalService getWithdrawalService() {
        return withdrawalService;
    }

    public WaitlistService getWaitlistService() {
        return waitlistService;
    }
}
