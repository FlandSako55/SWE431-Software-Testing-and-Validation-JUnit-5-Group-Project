package com.registration.service;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;

public class EnrollmentService {
    /**
     * Kayıtlı öğrenci sayısının dersin kontenjanından az olup olmadığını kontrol eder.
     *
     * @param course kontenjanı kontrol edilen ders
     * @return en az bir boş yer olup olmadığı
     * @throws IllegalArgumentException ders null ise
     */
    public boolean hasCapacity(Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Boş kontenjan varsa bilgileri doğrulanmış ve uygun bulunan öğrenciyi derse kaydeder.
     * Tekrar kayıtları öğrenci numarasıyla kontrol eder ve başarılı olursa her iki kaydı günceller.
     *
     * @param student kayıt isteğinde bulunan öğrenci
     * @param course kayıt istenen ders
     * @return başarılıysa {@code ENROLLED}. Ders doluysa veya öğrenci zaten
     *         derse ya da bekleme listesine kayıtlıysa değişiklik yapmadan {@code REJECTED}
     * @throws IllegalArgumentException argümanlardan biri null ise
     */
    public EnrollmentStatus enroll(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
