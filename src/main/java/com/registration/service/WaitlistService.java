package com.registration.service;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;

import java.util.function.Predicate;

public class WaitlistService {
    /**
     * Adds an already validated and eligible student to the tail of a full
     * course's waitlist. Checks roster and queue membership by student ID.
     *
     * @param student student requesting a waitlist place
     * @param course requested course
     * @return {@code WAITLISTED} on success, or {@code REJECTED} without changes
     *         if a seat is available or the student is already enrolled or queued
     * @throws IllegalArgumentException if either argument is null
     */
    public EnrollmentStatus addToWaitlist(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * When a seat is available, removes candidates in FIFO order until the first
     * eligible student who is not already enrolled is found. Updates the
     * promoted student's record and the course roster.
     *
     * @param course course whose waitlist is processed
     * @param eligibility eligibility check using each candidate's current credits
     * @return the promoted student, or {@code null} if no seat or candidate is available
     * @throws IllegalArgumentException if either argument is null
     */
    public Student promoteNextStudent(Course course, Predicate<Student> eligibility) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
