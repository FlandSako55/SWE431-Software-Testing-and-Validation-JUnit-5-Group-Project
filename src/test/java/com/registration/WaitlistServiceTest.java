package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WaitlistServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getWaitlistService());
    }

    // Servisi kendi dalınızda tamamlarken davranış testlerini buraya ekleyin.
}
