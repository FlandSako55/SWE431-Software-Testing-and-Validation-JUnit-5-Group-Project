package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnrollmentServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getEnrollmentService());
    }

    // Add service behavior tests here when implementing the branch.
}
