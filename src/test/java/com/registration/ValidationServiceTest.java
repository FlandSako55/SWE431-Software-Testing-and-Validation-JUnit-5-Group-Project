package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getValidationService());
    }

    // Servisi kendi dalınızda tamamlarken davranış testlerini buraya ekleyin.
}
