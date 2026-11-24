package com.registration.service;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;

public class EnrollmentService {
    /**
     * Checks whether the course has fewer enrolled students than available seats.
     *
     * @param course course whose capacity is checked
     * @return whether at least one seat is available
     * @throws IllegalArgumentException if the course is null
     */
    public boolean hasCapacity(Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Enrolls an already validated and eligible student when a seat is available.
     * Updates both enrollment records on success and checks duplicates by student ID.
     *
     * @param student student requesting enrollment
     * @param course requested course
     * @return {@code ENROLLED} on success, or {@code REJECTED} without changes
     *         if the course is full or the student is already enrolled or waitlisted
     * @throws IllegalArgumentException if either argument is null
     */
    public EnrollmentStatus enroll(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
