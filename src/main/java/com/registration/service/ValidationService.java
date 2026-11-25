package com.registration.service;

public class ValidationService {
    /**
     * Öğrenci numarasının tam dokuz ASCII rakamından oluşup oluşmadığını kontrol eder.
     *
     * @param studentId doğrulanacak öğrenci numarası
     * @return numara biçime uygunsa {@code true}, null ise {@code false}
     */
    public boolean isValidStudentId(String studentId) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }

    /**
     * Ders kodunun tamamının iki ila dört büyük ASCII harf ve ardından üç ASCII
     * rakamdan oluşup oluşmadığını kontrol eder (örneğin {@code SWE431}).
     *
     * @param courseCode doğrulanacak ders kodu
     * @return kod biçime uygunsa {@code true}, null ise {@code false}
     */
    public boolean isValidCourseCode(String courseCode) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
