package com.registration;

import com.registration.model.Course;
import com.registration.model.Student;
import com.registration.service.EligibilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EligibilityServiceTest {
    private EligibilityService service;
    private Course course;
    private Student ali;

    @BeforeEach
    void setUp() {
        service = new EligibilityService();
        course = new Course("SWE431", "Software Testing", 3, 40);
        course.addPrerequisite("SWE201");
        course.addPrerequisite("SWE301");

        ali = new Student("202412345", "Ali", true, 30);
        ali.addCompletedCourse("SWE201");
        ali.addCompletedCourse("SWE301");
    }

    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getEligibilityService());
    }

    // 1. Kural E1 (Valid): Tüm koşullar sağlandığında (ön koşullu ve ön koşulsuz derste) uygun döner.
    @Test
    void eligibleWhenAllConditionsHold() {
        Course elective = new Course("HIST101", "History", 2, 50);

        assertTrue(service.hasMetPrerequisites(ali, course));
        assertTrue(service.isEligible(ali, course, 15));    // 15 + 3 = 18 <= 30
        assertTrue(service.isEligible(ali, elective, 0));   // ön koşulsuz ders
    }

    // 2. Kural E2 (Invalid): Öğrenci pasifse ön koşullar ve kredi uygun olsa bile reddedilir, durum değişmez.
    @Test
    void inactiveStudentIsIneligible() {
        ali.setActive(false);

        assertFalse(service.isEligible(ali, course, 12));
        assertTrue(course.getEnrolledStudents().isEmpty());
        assertTrue(ali.getEnrolledCourses().isEmpty());
    }

    // 3. Kural E3 (Invalid): Ön koşul eksikse (yalnızca enrolledCourses içinde devam ediyor olsa bile) reddedilir.
    @Test
    void missingPrerequisiteIsIneligible() {
        Student ayse = new Student("202400001", "Ayse", true, 30);
        ayse.addCompletedCourse("SWE201");
        ayse.enrollInCourse("SWE301"); // devam eden ders tamamlanmış sayılmaz

        assertFalse(service.hasMetPrerequisites(ayse, course));
        assertFalse(service.isEligible(ayse, course, 12));
    }

    // 4. Kural E4 & Boundary Value Analysis: Kredi sınır değerleri (0, tam sınır 27+3=30, sınır aşımı 28+3=31).
    @ParameterizedTest
    @CsvSource({
            "0,  true",
            "27, true",
            "28, false"
    })
    void checksCreditLimitBoundaries(int currentCredits, boolean expectedEligible) {
        assertEquals(expectedEligible, service.isEligible(ali, course, currentCredits));
        assertTrue(course.getEnrolledStudents().isEmpty());
        assertTrue(ali.getEnrolledCourses().isEmpty());
    }

    // 5. Exception Assertion: Null argümanlar ve negatif kredi (-1) için IllegalArgumentException fırlatır.
    @Test
    void throwsForNullOrNegativeInputs() {
        assertThrows(IllegalArgumentException.class, () -> service.isEligible(null, course, 0));
        assertThrows(IllegalArgumentException.class, () -> service.isEligible(ali, null, 0));
        assertThrows(IllegalArgumentException.class, () -> service.isEligible(ali, course, -1));
        assertThrows(IllegalArgumentException.class, () -> service.hasMetPrerequisites(null, course));
        assertThrows(IllegalArgumentException.class, () -> service.hasMetPrerequisites(ali, null));
    }
}
