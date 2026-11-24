package com.registration.service;

import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;

import java.util.function.Predicate;

public class WaitlistService {
    /**
     * Bilgileri doğrulanmış ve uygun bulunan öğrenciyi, kontenjanı dolu dersin
     * bekleme listesinin sonuna ekler. Ders kaydını ve sırayı öğrenci numarasıyla kontrol eder.
     *
     * @param student bekleme listesine girmek isteyen öğrenci
     * @param course kayıt istenen ders
     * @return başarılıysa {@code WAITLISTED}. Kontenjan varsa veya öğrenci zaten
     *         derse ya da bekleme listesine kayıtlıysa değişiklik yapmadan {@code REJECTED}
     * @throws IllegalArgumentException argümanlardan biri null ise
     */
    public EnrollmentStatus addToWaitlist(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Boş kontenjan varsa adayları listeye giriş sırasıyla değerlendirir ve
     * derse henüz kayıtlı olmayan ilk uygun öğrenciye kadar kuyruktan çıkarır.
     * Derse alınan öğrencinin kaydını ve ders listesini günceller.
     *
     * @param course bekleme listesi işlenen ders
     * @param eligibility her adayın mevcut kredisini kullanan uygunluk kontrolü
     * @return derse alınan öğrenci, boş kontenjan veya uygun aday yoksa {@code null}
     * @throws IllegalArgumentException argümanlardan biri null ise
     */
    public Student promoteNextStudent(Course course, Predicate<Student> eligibility) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
