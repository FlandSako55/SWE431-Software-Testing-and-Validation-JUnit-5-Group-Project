package com.registration;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;
import com.registration.service.EligibilityService;
import com.registration.service.EnrollmentService;
import com.registration.service.ValidationService;
import com.registration.service.WaitlistService;
import com.registration.service.WithdrawalService;

import java.util.function.ToIntFunction;

/** Ders kaydı, kayıt silme ve bekleme listesinden öğrenci alma işlemlerinin giriş noktası. */
public class RegistrationSystem {
    private final ValidationService validationService = new ValidationService();
    private final EligibilityService eligibilityService = new EligibilityService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final WithdrawalService withdrawalService = new WithdrawalService();
    private final WaitlistService waitlistService = new WaitlistService();

    /**
     * İsteği doğrular ve uygun öğrenciyi derse kaydeder veya bekleme listesine alır.
     *
     * @param student kayıt isteğinde bulunan öğrenci
     * @param course kayıt istenen ders
     * @param currentCredits bu ders eklenmeden önce öğrencinin kayıtlı olduğu toplam kredi
     * @return kayıt veya bekleme listesi işleminin sonucu
     * @throws IllegalArgumentException öğrenci veya ders null ise ya da kredi negatifse
     */
    public EnrollmentStatus register(Student student, Course course, int currentCredits) {
        if (student == null || course == null || currentCredits < 0) {
            throw new IllegalArgumentException("Student and course are required, and credits must be nonnegative");
        }
        if (!validationService.isValidStudentId(student.getStudentId())
                || !validationService.isValidCourseCode(course.getCourseCode())
                || !eligibilityService.isEligible(student, course, currentCredits)) {
            return EnrollmentStatus.REJECTED;
        }
        if (enrollmentService.hasCapacity(course)) {
            return enrollmentService.enroll(student, course);
        }
        return waitlistService.addToWaitlist(student, course);
    }

    /** Varsa ders kaydını siler ve kaydın silinip silinmediğini döndürür. */
    public boolean withdraw(Student student, Course course) {
        return withdrawalService.withdraw(student, course);
    }

    /**
     * Boş kontenjana bekleme listesindeki ilk uygun öğrenciyi alır.
     *
     * @param course boş kontenjanı olan ders
     * @param currentCredits her adayın kayıtlı olduğu toplam krediyi sağlayan işlev
     * @return derse alınan öğrenci, kimse alınmadıysa {@code null}
     * @throws IllegalArgumentException ders veya kredi sorgulama işlevi null ise
     */
    public Student promoteNextStudent(Course course, ToIntFunction<Student> currentCredits) {
        if (course == null || currentCredits == null) {
            throw new IllegalArgumentException("Course and credit lookup are required");
        }
        return waitlistService.promoteNextStudent(course,
                student -> eligibilityService.isEligible(student, course, currentCredits.applyAsInt(student)));
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
