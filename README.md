# SWE431 Course Registration System

Java 17 ve Maven ile hazırlanmış bir ders kayıt sistemi iskeleti. Proje
klasöründe `mvn test` çalıştırın. JaCoCo raporu
`target/site/jacoco/index.html` dosyasında oluşur.

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
gönderir ve `main` için pull request açar. `main` dalına doğrudan push yapılmaz.
