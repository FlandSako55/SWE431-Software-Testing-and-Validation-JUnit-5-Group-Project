package com.registration.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Student {
    private final String studentId;
    private final String name;
    private boolean active;
    private final int maxCredits;
    private final Set<String> completedCourses = new LinkedHashSet<>();
    private final Set<String> enrolledCourses = new LinkedHashSet<>();

    public Student(String studentId, String name, boolean active, int maxCredits) {
        this.studentId = studentId;
        this.name = name;
        this.active = active;
        this.maxCredits = maxCredits;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public Set<String> getCompletedCourses() {
        return completedCourses;
    }

    public Set<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCompletedCourse(String courseCode) {
        completedCourses.add(courseCode);
    }

    public void enrollInCourse(String courseCode) {
        enrolledCourses.add(courseCode);
    }

    public void withdrawFromCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }
}
