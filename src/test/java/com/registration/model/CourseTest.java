package com.registration.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseTest {
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("SWE431", "Software Testing", 3, 2);
    }

    @Test
    void isEnrolledMatchesByStudentIdNotByObject() {
        course.addEnrolledStudent(new Student("202412345", "Ali", true, 30));

        assertTrue(course.isEnrolled("202412345"));
        assertFalse(course.isEnrolled("202499999"));
        assertFalse(course.isWaitlisted("202412345"));
    }

    @Test
    void isWaitlistedMatchesByStudentIdNotByObject() {
        course.addToWaitlist(new Student("202412345", "Ali", true, 30));

        assertTrue(course.isWaitlisted("202412345"));
        assertFalse(course.isWaitlisted("202499999"));
        assertFalse(course.isEnrolled("202412345"));
    }

    @Test
    void nullOrUnknownStudentIdIsNeverFound() {
        assertFalse(course.isEnrolled(null));
        assertFalse(course.isWaitlisted(null));
        assertFalse(course.isEnrolled("202412345"));
        assertFalse(course.isWaitlisted("202412345"));
    }

    @Test
    void studentIsNoLongerFoundAfterLeavingList() {
        Student student = new Student("202412345", "Ali", true, 30);
        course.addEnrolledStudent(student);
        course.removeEnrolledStudent(student);
        course.addToWaitlist(student);
        course.pollWaitlist();

        assertFalse(course.isEnrolled("202412345"));
        assertFalse(course.isWaitlisted("202412345"));
    }
}
