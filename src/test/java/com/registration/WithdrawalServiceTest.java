package com.registration;

import com.registration.model.Course;
import com.registration.model.Student;
import com.registration.service.WithdrawalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WithdrawalServiceTest {
    private WithdrawalService service;
    private Course course;
    private Student ali;

    @BeforeEach
    void setUp() {
        service = new WithdrawalService();
        course = new Course("SWE431", "Software Testing", 3, 2);

        // Ali derse kayıtlı: iki taraf da güncel
        ali = new Student("202412345", "Ali", true, 30);
        course.addEnrolledStudent(ali);
        ali.enrollInCourse("SWE431");
    }

    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getWithdrawalService());
    }

    // 1. Başarılı silme: öğrenci dersin listesinden çıkar.
    @Test
    void withdrawRemovesStudentFromCourse() {
        assertTrue(service.withdraw(ali, course));
        assertFalse(course.isEnrolled("202412345"));
        assertTrue(course.getEnrolledStudents().isEmpty());
    }

    // 2. Başarılı silme: ders öğrencinin kayıtlarından da çıkar.
    @Test
    void withdrawRemovesCourseFromStudent() {
        assertTrue(service.withdraw(ali, course));
        assertFalse(ali.getEnrolledCourses().contains("SWE431"));
    }

    // 3. Aynı numaralı farklı nesne: listedeki öğrenci numarasıyla bulunup silinir.
    @Test
    void withdrawFindsStudentByIdNotByObject() {
        Student sameId = new Student("202412345", "Ali", true, 30);
        sameId.enrollInCourse("SWE431");

        assertTrue(service.withdraw(sameId, course));
        assertFalse(course.isEnrolled("202412345"));
        assertTrue(sameId.getEnrolledCourses().isEmpty());
    }

    // 4. Derse kayıtlı olmayan öğrenci: false döner, hiçbir şey değişmez.
    @Test
    void notEnrolledStudentReturnsFalseWithoutChanges() {
        Student ayse = new Student("202400001", "Ayse", true, 30);
        ayse.enrollInCourse("CS101");

        assertFalse(service.withdraw(ayse, course));
        assertEquals(1, course.getEnrolledStudents().size());
        assertTrue(course.isEnrolled("202412345"));
        assertTrue(ayse.getEnrolledCourses().contains("CS101"));
        assertEquals(1, ayse.getEnrolledCourses().size());

        Student noId = new Student(null, "Can", true, 30); // numarası olmayan öğrenci de bulunamaz
        assertFalse(service.withdraw(noId, course));
        assertEquals(1, course.getEnrolledStudents().size());
    }

    // 5. Derste başka öğrenci de varsa yalnızca silinmek istenen öğrenci çıkar.
    @Test
    void withdrawLeavesOtherStudentsEnrolled() {
        Student ayse = new Student("202400001", "Ayse", true, 30);
        course.addEnrolledStudent(ayse);
        ayse.enrollInCourse("SWE431");

        assertTrue(service.withdraw(ali, course));
        assertEquals(1, course.getEnrolledStudents().size());
        assertTrue(course.isEnrolled("202400001"));
        assertTrue(ayse.getEnrolledCourses().contains("SWE431"));
    }

    // 6. Kayıt silmek bekleme listesinden öğrenci almaz (o iş promoteNextStudent'ta).
    @Test
    void withdrawDoesNotPromoteFromWaitlist() {
        Student mehmet = new Student("202400002", "Mehmet", true, 30);
        course.addToWaitlist(mehmet);

        assertFalse(service.withdraw(mehmet, course)); // bekleyen öğrenci kayıtlı sayılmaz
        assertTrue(service.withdraw(ali, course));
        assertTrue(course.isWaitlisted("202400002"));
        assertFalse(course.isEnrolled("202400002"));
        assertEquals(1, course.getWaitlist().size());
    }

    // 7. Exception Assertion: null öğrenci veya null ders IllegalArgumentException fırlatır.
    @Test
    void throwsForNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> service.withdraw(null, course));
        assertThrows(IllegalArgumentException.class, () -> service.withdraw(ali, null));
        assertTrue(course.isEnrolled("202412345"));
    }
}
