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
        if (student == null || course == null || currentCredits < 0) {
            throw new IllegalArgumentException("Student and course are required, and current credits must be nonnegative");
        }
        return student.isActive()
                && hasMetPrerequisites(student, course)
                && currentCredits + course.getCredits() <= student.getMaxCredits();
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
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course are required");
        }
        return student.getCompletedCourses().containsAll(course.getPrerequisites());
    }
}
