package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class EligibilityService {
    /**
     * Öğrencinin aktif olup olmadığını, ön koşulları tamamlayıp tamamlamadığını
     * ve dersi eklediğinde kredi sınırını aşıp aşmadığını kontrol eder.
     *
     * @param student kayıt olmak isteyen öğrenci
     * @param course kayıt istenen ders
     * @param currentCredits bu ders eklenmeden önce öğrencinin kayıtlı olduğu toplam kredi
     * @return tüm uygunluk koşullarının karşılanıp karşılanmadığı
     * @throws IllegalArgumentException öğrenci veya ders null ise ya da mevcut kredi negatifse
     */
    public boolean isEligible(Student student, Course course, int currentCredits) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Öğrencinin dersin tüm ön koşullarını tamamlayıp tamamlamadığını kontrol eder.
     *
     * @param student tamamladığı dersler kontrol edilen öğrenci
     * @param course ön koşulları kontrol edilen ders
     * @return tüm ön koşullar karşılanmışsa veya ön koşul yoksa {@code true}
     * @throws IllegalArgumentException argümanlardan biri null ise
     */
    public boolean hasMetPrerequisites(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
