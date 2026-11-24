package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class WaitlistService {
    /**
     * Adds a student to the end of a course's waitlist.
     *
     * @param student student to add
     * @param course course whose waitlist is updated
     */
    public void addToWaitlist(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Promotes and returns the next student in a course's waitlist.
     *
     * @param course course whose waitlist is processed
     * @return the next student to promote, or {@code null} if the waitlist is empty
     */
    public Student promoteNextStudent(Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
