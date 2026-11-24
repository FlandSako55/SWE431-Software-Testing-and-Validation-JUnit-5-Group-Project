package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WaitlistServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getWaitlistService());
    }

    // Add service behavior tests here when implementing the branch.
}
