package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnrollmentServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getEnrollmentService());
    }

    // Servisi kendi dalınızda tamamlarken davranış testlerini buraya ekleyin.
}
