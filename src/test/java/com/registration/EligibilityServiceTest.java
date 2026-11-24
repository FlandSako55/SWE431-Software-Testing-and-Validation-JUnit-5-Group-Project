package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EligibilityServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getEligibilityService());
    }

    // Add service behavior tests here when implementing the branch.
}
