package com.registration.service;

import java.util.regex.Pattern;

public class ValidationService {
    private static final Pattern STUDENT_ID = Pattern.compile("[0-9]{9}");
    private static final Pattern COURSE_CODE = Pattern.compile("[A-Z]{2,4}[0-9]{3}");

    /**
     * Checks whether the entire student ID consists of exactly nine ASCII digits.
     *
     * @param studentId the student ID to validate
     * @return {@code true} if the ID matches the format; {@code false} otherwise
     */
    public boolean isValidStudentId(String studentId) {
        return studentId != null && STUDENT_ID.matcher(studentId).matches();
    }

    /**
     * Checks whether the entire course code consists of two to four uppercase
     * ASCII letters followed by exactly three ASCII digits.
     *
     * @param courseCode the course code to validate
     * @return {@code true} if the code matches the format; {@code false} otherwise
     */
    public boolean isValidCourseCode(String courseCode) {
        return courseCode != null && COURSE_CODE.matcher(courseCode).matches();
    }
}
