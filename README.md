# SWE431 Course Registration System

Java 17 ve Maven ile hazırlanmış bir ders kayıt sistemi iskeleti. Proje
klasöründe `mvn test` çalıştırın. JaCoCo raporu
`target/site/jacoco/index.html` dosyasında oluşur.
jacoco nedir?
--
pom.xml dosyasına JaCoCo eklentisini koyduğunuzda, terminalde mvn test komutunu çalıştırmanızla birlikte proje klasöründe otomatik olarak target/site/jacoco/index.html adında görsel bir rapor oluşur.

Bu dosyayı tarayıcıda açtığınızda her sınıfın kapsam yüzdesini ve kod satırlarının renk kodlarını görürsünüz:

Yeşil: Testlerin tamamen çalıştırdığı satırlar.

Sarı: Koşulun sadece bir tarafının test edildiği satırlar (örneğin if test edilmiş ama else edilmemiş).

Kırmızı: Hiçbir testin uğramadığı satırlar.


--
## Örnek ekip çalışması

İskelet `main` dalına birleştirildikten sonra her üye güncel `main`
üzerinden kendi görev dalını oluşturur. Üye dalları önceden açılmaz.
Örneğin Üye 1, klonladığı repo klasöründe şunları çalıştırır.

```sh
git switch main
git pull --ff-only
git switch -c feat/validation
```

Üye 1, `src/main/java/com/registration/service/ValidationService.java`
dosyasındaki TODO'ları tamamlar. Öğrenci numaralarını JUnit test verisi
olarak `src/test/java/com/registration/ValidationServiceTest.java` dosyasına
yazar. Örneğin, aşağıdaki metodu test sınıfının içine ekler. IDE üzerinden
`ValidationService`, `ParameterizedTest`, `CsvSource` ve `assertEquals`
içe aktarımlarını ekleyebilir.

```java
@ParameterizedTest
@CsvSource({"202412345, true", "20241234, false"})
void checksStudentId(String studentId, boolean expected) {
    assertEquals(expected, new ValidationService().isValidStudentId(studentId));
}
```

`mvn "-Dtest=ValidationServiceTest" test` bu test sınıfını çalıştırır.
Her üye kendi servis ve test dosyasını commit eder, kendi dalını GitHub'a
gönderir ve `main` için pull request açar. `main` dalına doğrudan push yapılmaz(kaldıabilirim ama birisi yanlışlıkla pusha atarsa contributorsta claude simgesi görebiliriz).

## Ortak kurallar

- Öğrencinin derse kayıtlı ya da bekleme listesinde olup olmadığı **öğrenci
  numarasıyla** kontrol edilir. `Student` sınıfında `equals` olmadığı için
  `contains(student)` kullanılmaz. Bunun yerine `Course.isEnrolled(studentId)`
  ve `Course.isWaitlisted(studentId)` çağrılır.
- `REJECTED` dönen bir istekte ders listesi, bekleme listesi ve öğrencinin
  kayıtları değişmez. Testler bunu da kontrol eder.
- Model sınıflarına ve `RegistrationSystem` sınıfına dokunmadan önce ekibe
  haber verilir.

## Üye 3: EnrollmentService

- Başarılı kayıtta iki yer güncellenir: `course.addEnrolledStudent(student)`
  ve `student.enrollInCourse(course.getCourseCode())`.
- Ders doluysa ya da öğrenci zaten derste veya bekleme listesindeyse `enroll`
  hiçbir şeyi değiştirmeden `REJECTED` döner. İstisna fırlatmaz.
- `hasCapacity` için kontenjanı 0 olan ders, tam dolu ders ve tek boş yeri
  kalan ders test edilir.

## Üye 4: WaitlistService

- `addToWaitlist`, derste boş yer varsa ya da öğrenci zaten derste veya
  bekleme listesindeyse `REJECTED` döner. Bekleme listesine giren öğrencinin
  `enrolledCourses` kümesine ders eklenmez.
- `promoteNextStudent` için:
  - Boş yer yoksa kuyruğa dokunmadan `null` döner.
  - Adaylar giriş sırasıyla kuyruktan çıkarılır. Uygun olmayan ya da zaten
    kayıtlı adaylar atlanır ve kuyruğa geri eklenmez.
  - İlk uygun aday Üye 3'teki gibi iki yerde de güncellenir ve döndürülür.
    Uygun aday yoksa `null` döner.
- Uygunluk kontrolü `Predicate` olarak geldiği için testlerde `s -> true` ve
  `s -> false` kullanılabilir. Bu testler EligibilityService'i beklemez.

## Üye 5: WithdrawalService

- Öğrenci ders listesinden numarasıyla bulunarak çıkarılır. Listedeki nesne
  ile parametre olarak gelen nesne farklı olabilir.
