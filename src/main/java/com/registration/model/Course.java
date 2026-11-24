package com.registration.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Course {
    private final String courseCode;
    private final String title;
    private final int credits;
    private final int capacity;
    private final Set<String> prerequisites = new LinkedHashSet<>();
    private final List<Student> enrolledStudents = new ArrayList<>();
    private final Queue<Student> waitlist = new LinkedList<>();

    public Course(String courseCode, String title, int credits, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Queue<Student> getWaitlist() {
        return waitlist;
    }

    public void addPrerequisite(String courseCode) {
        prerequisites.add(courseCode);
    }

    public void addEnrolledStudent(Student student) {
        enrolledStudents.add(student);
    }

    public void removeEnrolledStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void addToWaitlist(Student student) {
        waitlist.add(student);
    }

    public Student pollWaitlist() {
        return waitlist.poll();
    }

    /**
     * Verilen numaraya sahip öğrencinin derse kayıtlı olup olmadığını kontrol eder.
     * Öğrenci nesnelerini değil öğrenci numaralarını karşılaştırır.
     *
     * @param studentId aranan öğrenci numarası
     * @return bu numarayla kayıtlı bir öğrenci varsa {@code true}, null ise {@code false}
     */
    public boolean isEnrolled(String studentId) {
        return containsStudentId(enrolledStudents, studentId);
    }

    /**
     * Verilen numaraya sahip öğrencinin bekleme listesinde olup olmadığını kontrol eder.
     * Öğrenci nesnelerini değil öğrenci numaralarını karşılaştırır.
     *
     * @param studentId aranan öğrenci numarası
     * @return bu numarayla bekleyen bir öğrenci varsa {@code true}, null ise {@code false}
     */
    public boolean isWaitlisted(String studentId) {
        return containsStudentId(waitlist, studentId);
    }

    private static boolean containsStudentId(Collection<Student> students, String studentId) {
        if (studentId == null) {
            return false;
        }
        for (Student student : students) {
            if (studentId.equals(student.getStudentId())) {
                return true;
            }
        }
        return false;
    }
}
