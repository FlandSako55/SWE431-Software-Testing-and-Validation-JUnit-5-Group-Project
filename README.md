# SWE431 Course Registration System

Java 17 ve Maven ile hazırlanmış, beş kişilik bir ders kayıt sistemi iskeleti.
`Student` ve `Course` ortak modellerdir. `RegistrationSystem` doğrulama,
uygunluk, kayıt, kayıt silme ve bekleme listesi servislerini bir araya getirir.

## Ekip görevleri

| Üye | Servis ve test | Sorumluluk | Dal |
| --- | --- | --- | --- |
| 1 | `ValidationService` / `ValidationServiceTest` | Öğrenci numarası ve ders kodu biçimi | `feat/validation` |
| 2 | `EligibilityService` / `EligibilityServiceTest` | Aktiflik, ön koşullar ve kredi sınırı | `feat/eligibility` |
| 3 | `EnrollmentService` / `EnrollmentServiceTest` | Kontenjan ve mükerrer kayıt | `feat/enrollment` |
| 4 | `WithdrawalService` / `WithdrawalServiceTest` | Ders kaydını silme | `feat/withdrawal` |
| 5 | `WaitlistService` / `WaitlistServiceTest` | Bekleme sırası ve boş koltuğa öğrenci alma | `feat/waitlist` |

## Servis sözleşmesi

- Her üye yalnızca kendi servis ve test dosyasını değiştirir. Servis
  Javadoc'ları beklenen davranışı tanımlar. Her servisteki TODO gövdelerini
  tamamlayın ve geçerli, geçersiz ve sınır değerleri kapsayan en az dört
  JUnit 5 test senaryosu ekleyin.
- Öğrenci numarası tam dokuz ASCII rakamından oluşur. Ders kodu iki ila dört
  büyük ASCII harfinin ardından üç ASCII rakamı içerir (ör. `SWE431`).
  Doğrulama metotları biçim hatası ve `null` için `false` döndürür.
- Zorunlu nesnenin eksik olması veya negatif mevcut kredi gibi hatalı
  argümanlarda `IllegalArgumentException` kullanılır.
  `EnrollmentStatus` kayıt sonucunu `ENROLLED`, `WAITLISTED` veya
  `REJECTED` olarak bildirir. Başarısız uygunluk kontrolü ve mükerrer istek
  normal ret durumlarıdır. Kayıt silme metodu kayıt bulunamazsa `false` döndürür.
- Mevcut kredi toplamı uygunluk kontrolüne çağıran tarafından iletilir.
  Başarılı kayıt ve kayıt silme, ders ve öğrenci kayıtlarını birlikte
  günceller. Boş koltuğa bekleme listesinden öğrenci alma ayrı bir çağrıdır
  ve adayların uygunluğunu sırayla yeniden kontrol eder.

### Parametreli test örneği

Aşağıdaki örnekleri, servis mantığını tamamladıktan sonra `ValidationServiceTest`
sınıfına ekleyin. JUnit 5'ten `ParameterizedTest`, `ValueSource`,
`NullAndEmptySource` ve `Assertions` içindeki doğrulamaları içe aktarın.

```java
private final ValidationService validationService = new ValidationService();

@ParameterizedTest
@ValueSource(strings = {"202412345", "190000001"})
void shouldAcceptValidStudentIds(String validId) {
    assertTrue(validationService.isValidStudentId(validId));
}

@ParameterizedTest
@NullAndEmptySource
@ValueSource(strings = {"12345", "20241234", "20241234A", "2024123456"})
void shouldRejectInvalidAndBoundaryStudentIds(String invalidId) {
    assertFalse(validationService.isValidStudentId(invalidId));
}
```

## Testler ve kapsam raporu

JDK 17 ve Maven 3.9 ile çalıştırın.

```sh
mvn test
mvn "-Dtest=ValidationServiceTest" test
```

JaCoCo, test aşamasında [HTML kapsam raporunu](target/site/jacoco/index.html)
üretir. Başlangıç testleri servislerin facade üzerinden erişilebilir
olduğunu kontrol eder. Servis davranışı testleri üye dallarında eklenir.

## Git ve pull request akışı

1. Repoyu klonlayın ve kendi görev dalınızı açın. Aşağıdaki örnek Üye 1 içindir.

   ```sh
   git clone https://github.com/FlandSako55/SWE431-Software-Testing-and-Validation-JUnit-5-Group-Project.git course-registration-system
   cd course-registration-system
   git switch -c feat/validation
   ```

2. Kendi servis ve test dosyanızı tamamlayın, ardından `mvn test` çalıştırın.
3. Yalnızca kendi dosyalarınızı commit edin ve özellik dalınızı gönderin.

   ```sh
   git add src/main/java/com/registration/service/ValidationService.java src/test/java/com/registration/ValidationServiceTest.java
   git commit -m "Implement ValidationService and parameterized tests"
   git push -u origin feat/validation
   ```

4. GitHub'da **Compare & pull request** ile `main` dalını hedefleyen bir PR
   açın. `main` dalına doğrudan push yapmayın. Proje sorumlusu testleri kontrol
   ettikten sonra PR'ı birleştirir. Üye commit'leri ve PR'ları katkı kaydını
   oluşturur.
