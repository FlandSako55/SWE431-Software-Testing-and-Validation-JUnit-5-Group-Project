package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EligibilityServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getEligibilityService());
    }

    // Servisi kendi dalınızda tamamlarken davranış testlerini buraya ekleyin.
}
