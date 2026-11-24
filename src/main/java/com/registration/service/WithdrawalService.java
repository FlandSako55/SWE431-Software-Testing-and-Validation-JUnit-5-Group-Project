package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class WithdrawalService {
    /**
     * Finds the enrollment by student ID and removes the course from the
     * enrolled student's record and the student from the course roster.
     * Waitlist promotion is a separate operation.
     *
     * @param student student requesting withdrawal
     * @param course course to withdraw from
     * @return whether an enrollment was removed
     * @throws IllegalArgumentException if either argument is null
     */
    public boolean withdraw(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
