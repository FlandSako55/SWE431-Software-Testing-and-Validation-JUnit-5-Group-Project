package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class EligibilityService {
    /**
     * Checks whether the student is active, has completed the prerequisites,
     * and can add this course without exceeding the credit limit.
     *
     * @param student student seeking enrollment
     * @param course requested course
     * @param currentCredits total credits already enrolled, before adding this course
     * @return whether all eligibility requirements are met
     * @throws IllegalArgumentException if either object is null or current credits are negative
     */
    public boolean isEligible(Student student, Course course, int currentCredits) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Checks whether the student has completed every prerequisite for the course.
     *
     * @param student student whose completed courses are checked
     * @param course course whose prerequisites are checked
     * @return whether all prerequisites are complete, including when none are required
     * @throws IllegalArgumentException if either argument is null
     */
    public boolean hasMetPrerequisites(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
