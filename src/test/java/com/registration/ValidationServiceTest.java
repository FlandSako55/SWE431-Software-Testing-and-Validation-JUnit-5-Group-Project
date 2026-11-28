package com.registration;

import com.registration.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationServiceTest {
    private final ValidationService service = new ValidationService();

    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getValidationService());
    }

    @ParameterizedTest
    @CsvSource({
            "20241234, false",
            "202412345, true",
            "2024123456, false",
            "000000001, true"
    })
    void checksStudentIdLengthAndLeadingZeros(String studentId, boolean expected) {
        assertEquals(expected, service.isValidStudentId(studentId));
    }

    @ParameterizedTest
    @CsvSource({
            "20241234a, false",
            "20241234٣, false",
            "' 202412345', false",
            "'202412345 ', false",
            "'2024 2345', false",
            "'         ', false",
            "'202412345\n', false"
    })
    void rejectsStudentIdsWithInvalidCharacters(String studentId, boolean expected) {
        assertEquals(expected, service.isValidStudentId(studentId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void rejectsMissingStudentIds(String studentId) {
        assertFalse(service.isValidStudentId(studentId));
    }

    @ParameterizedTest
    @CsvSource({
            "S431, false",
            "CS101, true",
            "SWE431, true",
            "MATH201, true",
            "SWEXY431, false",
            "SWE43, false",
            "SWE4310, false"
    })
    void checksCourseCodeLengthBoundaries(String courseCode, boolean expected) {
        assertEquals(expected, service.isValidCourseCode(courseCode));
    }

    @ParameterizedTest
    @CsvSource({
            "swe431, false",
            "SwE431, false",
            "ŞWE431, false",
            "SWE43٣, false",
            "SW1431, false",
            "xSWE431, false",
            "SWE431x, false",
            "' SWE431', false",
            "'SWE431 ', false",
            "'SW 431', false",
            "'      ', false",
            "'SWE431\n', false"
    })
    void rejectsCourseCodesWithInvalidCharacters(String courseCode, boolean expected) {
        assertEquals(expected, service.isValidCourseCode(courseCode));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void rejectsMissingCourseCodes(String courseCode) {
        assertFalse(service.isValidCourseCode(courseCode));
    }
}
