package com.registration;

import com.registration.service.EligibilityService;
import com.registration.service.EnrollmentService;
import com.registration.service.ValidationService;
import com.registration.service.WaitlistService;
import com.registration.service.WithdrawalService;

public class RegistrationSystem {
    private final ValidationService validationService;
    private final EligibilityService eligibilityService;
    private final EnrollmentService enrollmentService;
    private final WithdrawalService withdrawalService;
    private final WaitlistService waitlistService;

    public RegistrationSystem() {
        validationService = new ValidationService();
        eligibilityService = new EligibilityService();
        enrollmentService = new EnrollmentService();
        withdrawalService = new WithdrawalService();
        waitlistService = new WaitlistService();
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public EligibilityService getEligibilityService() {
        return eligibilityService;
    }

    public EnrollmentService getEnrollmentService() {
        return enrollmentService;
    }

    public WithdrawalService getWithdrawalService() {
        return withdrawalService;
    }

    public WaitlistService getWaitlistService() {
        return waitlistService;
    }
}
