package com.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WithdrawalServiceTest {
    @Test
    void facadeProvidesService() {
        assertNotNull(new RegistrationSystem().getWithdrawalService());
    }

    // Servisi kendi dalınızda tamamlarken davranış testlerini buraya ekleyin.
}
