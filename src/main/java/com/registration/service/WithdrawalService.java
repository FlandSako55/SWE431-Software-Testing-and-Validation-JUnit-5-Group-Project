package com.registration.service;

import com.registration.model.Course;
import com.registration.model.Student;

public class WithdrawalService {
    /**
     * Kaydı öğrenci numarasıyla bulur, dersi öğrencinin kaydından ve öğrenciyi
     * ders listesinden çıkarır.
     * Bekleme listesinden öğrenci alma ayrı bir işlemdir.
     *
     * @param student ders kaydını sildirmek isteyen öğrenci
     * @param course kaydın silineceği ders
     * @return ders kaydının silinip silinmediği
     * @throws IllegalArgumentException argümanlardan biri null ise
     */
    public boolean withdraw(Student student, Course course) {
        throw new UnsupportedOperationException("TODO: Implement in your branch");
    }
}
