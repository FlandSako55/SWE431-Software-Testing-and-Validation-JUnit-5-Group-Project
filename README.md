# SWE431 Course Registration System

Beş kişilik ekip için Java 17 ve Maven tabanlı ders kayıt sistemi iskeleti.

## Çalıştırma

JDK 17 ve Maven ile proje klasöründe `mvn test` çalıştırın.
JaCoCo raporu `target/site/jacoco/index.html` dosyasında oluşur.

## Örnek ekip çalışması

Üye 1, `git switch -c feat/validation` ile kendi dalını açar.
`ValidationService` içindeki TODO'ları tamamlar ve `ValidationServiceTest`
sınıfına testlerini ekler. Örneğin, bir parametreli test dokuz haneli
`202412345` değerini kabul ederken sekiz haneli `20241234` değerini reddeder.

```sh
mvn "-Dtest=ValidationServiceTest" test
```

Her üye sadece kendi servis ve test dosyasını değiştirir. Testler geçince
değişikliklerini commit eder, kendi dalını GitHub'a gönderir ve `main` için
bir pull request açar. `main` dalına doğrudan push yapılmaz.
