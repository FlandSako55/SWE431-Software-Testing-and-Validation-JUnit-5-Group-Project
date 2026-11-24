package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class EligibilityService {
    /**
     * Determines whether a student is eligible to enroll in a course, including
     * account status and prerequisite requirements.
     *
     * @param student student seeking enrollment
     * @param course course the student wants to take
     * @return {@code true} when the student is eligible
     */
    public boolean isEligible(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Determines whether a student has completed every prerequisite for a course.
     *
     * @param student student whose completed courses are checked
     * @param course course whose prerequisites are checked
     * @return {@code true} when all prerequisites are complete
     */
    public boolean hasMetPrerequisites(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
