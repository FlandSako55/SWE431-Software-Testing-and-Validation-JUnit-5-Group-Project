# SWE431 Course Registration System

(`Student`, `Course`, `EnrollmentStatus`) ve giriş noktası
(`RegistrationSystem`) hazırdır. Beş servis `TODO` olarak bırakılmıştır ve her
üye birini tamamlayıp JUnit 5 testlerini yazar.

## Görev dağılımı

| Üye | Rol | Dal | Servis dosyası | Ek teslim |
|---|---|---|---|---|
| 1 | Validation + Parameterized Test + README | `feat/validation` | `ValidationService.java` | README'nin bakımı |
| 2 | Eligibility & Prerequisites + Decision Table + Test Design | `feat/eligibility` | `EligibilityService.java` | `docs/decision-table.md`, `docs/test-design.md` |
| 3 | Capacity & Duplicate + Exception Assertion + Coverage | `feat/enrollment` | `EnrollmentService.java` | `docs/coverage/` (JaCoCo raporu) |
| 4 | Course Withdrawal + Regression Test + Contribution Record | `feat/withdrawal` | `WithdrawalService.java` | `docs/regression.md`, `docs/contribution-record.md` |
| 5 (lider) | Waitlist & Promotion + CFG & Basis Paths + Entegrasyon | `feat/waitlist` | `WaitlistService.java` | `docs/cfg-basis-paths.md`, `RegistrationSystemTest.java` |

Servis dosyaları `src/main/java/com/registration/service/` altında, test
dosyaları `src/test/java/com/registration/` altındadır. Her üye kendi
servisinin `XxxServiceTest.java` dosyasına yazar. Dokümanlar `docs/`
klasörüne Markdown olarak konur.

**Birleştirme sırası:** Validation → Eligibility → Enrollment ve Withdrawal →
Waitlist → entegrasyon testi ve coverage raporu. Herkes aynı anda
çalışabilir; sıra yalnızca PR'ların `main`'e giriş sırasıdır.

## 1. Kurulum

Gerekenler:

- **JDK 17, 21 veya 25** (Temurin veya Oracle). `java -version` en az 17
  göstermeli. Proje Java 17 seviyesinde derlenir; daha eski JDK'lar (8, 11)
  çalışmaz. Kapsama aracı JaCoCo'nun bu sürümü resmi olarak en fazla JDK 25'i
  destekler, daha yenisi kuruluysa sorun çıkarsa 21 veya 25 kullanın.
- **Maven**. `mvn -version` ile kontrol edin. IntelliJ IDEA kullanıyorsanız
  kendi Maven'i yeterlidir; sağdaki Maven panelinden `test` çalıştırılabilir.
- **Git**. `git --version` ile kontrol edin.



Repoyu klonlayın ve testleri çalıştırın:

```sh
git clone https://github.com/FlandSako55/SWE431-Software-Testing-and-Validation-JUnit-5-Group-Project.git
cd SWE431-Software-Testing-and-Validation-JUnit-5-Group-Project
mvn test
```

`BUILD SUCCESS` görüyorsanız kurulum tamamdır. IntelliJ'de **File → Open** ile
klasördeki `pom.xml` dosyasını açın ve "Open as Project" deyin.

## 2. Git akışı

`main` dalına doğrudan push yapılamaz. Her değişiklik kendi dalınızda yapılır
ve pull request (PR) ile `main`'e girer.

**Dalınızı açın** (tablodaki dal adını kullanın):

```sh
git switch main
git pull
git switch -c feat/validation
```

**Çalışırken sık sık commit atın:**

```sh
git status
git add src/main/java/com/registration/service/ValidationService.java src/test/java/com/registration/ValidationServiceTest.java
git commit -m "Implement student id validation"
```

Yalnızca kendi servis, test ve doküman dosyalarınızı ekleyin. `git add .` kullanmayın.

**GitHub'a gönderin:**

```sh
git push -u origin feat/validation   # ilk seferde
git push                              # sonraki seferlerde
```

**PR açın:** GitHub'da repoya girin, sarı **Compare & pull request**
butonuna basın. `base: main` ← `compare: feat/...` olduğunu kontrol edin.
Açıklamaya neleri test ettiğinizi yazın.

**Birleştirme:** Düzeltme gerekirse aynı dalda commit'leyip push edin; PR
kendiliğinden güncellenir. PR hazır olunca **Squash and merge** ile
birleştirilir.

**Başkasının PR'ı `main`'e girdiyse dalınızı güncelleyin:**

```sh
git switch feat/validation
git pull origin main
```

**Çakışma (conflict) çıkarsa:** Git hangi dosyada olduğunu söyler. Dosyada
`<<<<<<<`, `=======`, `>>>>>>>` işaretleri arasındaki iki versiyondan doğru
olanı bırakın, işaretleri silin, sonra:

```sh
git add <dosya>
git commit
git push
```

Emin değilseniz dosyayı değiştirmeden gruba yazın.

## 3. Ortak kurallar

- Öğrencinin derse kayıtlı ya da bekleme listesinde olup olmadığı **öğrenci
  numarasıyla** kontrol edilir. `Student` sınıfında `equals` olmadığı için
  `contains(student)` kullanılmaz. Bunun yerine `Course.isEnrolled(studentId)`
  ve `Course.isWaitlisted(studentId)` çağrılır.
- `REJECTED` dönen bir istekte ders listesi, bekleme listesi ve öğrencinin
  kayıtları değişmez. Testler bunu da kontrol eder.
- `ValidationService` geçersiz veya null girdide `false` döner. Diğer
  servisler null argümanda `IllegalArgumentException` fırlatır.
- Yalnızca kendi servis ve test dosyanızı değiştirin. Model sınıflarına,
  `RegistrationSystem` sınıfına veya `pom.xml` dosyasına dokunmanız gerekirse
  önce gruba haber verin.
- Metotların ne yapması gerektiği servis dosyalarındaki Javadoc
  yorumlarında yazar. Önce yorumu okuyun, bu README onu tamamlar.

## 4. Üye görevleri

### Üye 1: ValidationService + Parameterized Test + README

> **Ödev bu bölümde ne istiyor:** Aynı testi farklı girdilerle tekrar tekrar
> yazmak yerine **parameterized test** kullanmamızı istiyor: tek test metodu,
> bir veri tablosundaki her satır için ayrı çalışır. Girdiler
> **equivalence partitioning** (geçerli/geçersiz gruplar) ve **boundary value**
> (8, 9, 10 hane gibi sınırlar) mantığıyla seçilmeli. Ayrıca projenin nasıl
> kurulup çalıştırılacağını anlatan bir **README** teslim ediliyor.


- `isValidStudentId`: Tam **9 ASCII rakam**. Null, boş metin, harf, boşluk,
  8 veya 10 hane `false`.
  - `202412345` ✓ · `20241234` ✗ · `2024123456` ✗ · `20241234a` ✗ ·
    `" 202412345"` ✗ · `null` ✗
- `isValidCourseCode`: **2–4 büyük ASCII harf + 3 ASCII rakam**, kodun
  tamamı bu biçimde olmalı (tam eşleşme).
  - `SWE431` ✓ · `CS101` ✓ · `MATH201` ✓ · `swe431` ✗ · `S431` ✗ ·
    `SWEXY431` ✗ · `SWE43` ✗ · `SWE4310` ✗ · `xSWE431` ✗ · `null` ✗
- Türkçe veya Unicode rakam/harf (`Ş`, `٣` gibi) geçersizdir. `\d` yerine
  `[0-9]`, `[A-Z]` kullanın.
- **Parameterized Test:** Testlerin çoğu `@ParameterizedTest` ile yazılır.
  `@CsvSource` ile geçerli/geçersiz örnekler, `@NullAndEmptySource` ile null
  ve boş metin denenir. Örnek (`ValidationServiceTest.java`):

```java
@ParameterizedTest
@CsvSource({"202412345, true", "20241234, false", "20241234a, false"})
void checksStudentId(String studentId, boolean expected) {
    assertEquals(expected, new ValidationService().isValidStudentId(studentId));
}
```

- **README:** Proje bitene kadar README'yi güncel tutar. Diğer üyelerin
  dokümanlarına bağlantı verir ve "nasıl çalıştırılır" kısmını son hâline
  getirir.

### Üye 2: EligibilityService + Decision Table + Test Design

> **Ödev bu bölümde ne istiyor:** Birden çok koşulun birlikte sonucu belirlediği
> durumlar için **decision table testing**: koşulların (aktif mi, ön koşul,
> kredi, kontenjan, tekrar kayıt) bütün anlamlı kombinasyonları tabloya dökülür,
> her kural (sütun) bir test olur. Ayrıca grubun **test design** dokümanı:
> hangi servis için hangi test tekniğinin neden seçildiği ve test listesi.


- `isEligible` üç koşulun **hepsi** sağlanırsa `true` döner:
  1. `student.isActive()` true.
  2. `hasMetPrerequisites(student, course)` true.
  3. `currentCredits + course.getCredits() <= student.getMaxCredits()`.
     Sınıra tam eşit olmak **uygundur**, aşmak uygun değildir.
- `isEligible`: öğrenci veya ders null ya da `currentCredits < 0` ise
  `IllegalArgumentException`.
- `hasMetPrerequisites`: dersin bütün ön koşulları öğrencinin
  `getCompletedCourses()` kümesinde olmalı. Ön koşul yoksa `true`. Argüman
  null ise `IllegalArgumentException`.
- **Enrollment Decision Table (`docs/decision-table.md`):** `register`
  akışının koşulları (geçerli numara, geçerli ders kodu, aktif, ön koşul,
  kredi, boş yer, zaten kayıtlı/bekliyor) ve sonuçları (`ENROLLED`,
  `WAITLISTED`, `REJECTED`) tablo hâlinde yazılır. Her kural sütunu için bir
  test yazılır ve tabloda test adı belirtilir.
- **Test Design (`docs/test-design.md`):** Grubun genel test tasarım
  dosyası. Her servis için kullanılan teknikler (equivalence partitioning,
  boundary value, decision table, basis path) ve test listesi. Diğer
  üyelerden kendi bölümlerini ister ve birleştirir.

**Örnek (`EligibilityServiceTest.java`):**

```java
@Test
void creditsExactlyAtLimitAreEligible() {
    Student ali = new Student("202412345", "Ali", true, 30);
    Course course = new Course("SWE431", "Software Testing", 3, 40);
    assertTrue(new EligibilityService().isEligible(ali, course, 27)); // 27 + 3 = 30
}
```

**Örnek (`docs/decision-table.md`):**

| Koşul / Kural | R1 | R2 | R3 |
|---|---|---|---|
| Öğrenci aktif | E | H | E |
| Ön koşullar tamam | E | – | E |
| Kredi yetiyor | E | – | E |
| Boş yer var | E | – | H |
| **Sonuç** | ENROLLED | REJECTED | WAITLISTED |
| **Test** | `enrollsWhenAllConditionsHold` | `rejectsInactiveStudent` | `waitlistsWhenCourseIsFull` |

### Üye 3: EnrollmentService + Exception Assertion + Coverage

> **Ödev bu bölümde ne istiyor:** Kontenjan için **boundary value analysis**
> (`max-1`, `max`, `max+1`), hatalı girdilerde doğru istisnanın fırlatıldığını
> gösteren **exception assertion** (`assertThrows`) ve testlerin kodun ne
> kadarını çalıştırdığını gösteren **code coverage** raporu (JaCoCo; satır ve
> dal kapsaması).


- `hasCapacity`: kayıtlı öğrenci sayısı kontenjandan azsa `true`. Ders null
  ise `IllegalArgumentException`.
- `enroll` başarılıysa iki yer güncellenir: `course.addEnrolledStudent(student)`
  ve `student.enrollInCourse(course.getCourseCode())`, sonra `ENROLLED`.
- Ders doluysa ya da öğrenci zaten derste veya bekleme listesindeyse
  (**duplicate**) `enroll` hiçbir şeyi değiştirmeden `REJECTED` döner.
  İstisna fırlatmaz.
- **Kontenjan sınır değerleri:** kayıtlı sayısı `max-1`, `max` ve
  `max+1`'e zorlanarak test edilir. Kontenjanı 0 olan ders de denenir.
- **Exception Assertion:** Bütün null argüman durumları
  `assertThrows(IllegalArgumentException.class, ...)` ile test edilir.
- **Coverage (`docs/coverage/`):** Bütün servisler `main`'e girdikten sonra
  `main` üzerinde `mvn test` çalıştırılır. `target/site/jacoco/` içindeki
  rapor `docs/coverage/` klasörüne kopyalanır ve `docs/coverage/README.md`
  içine her sınıfın satır ve dal kapsama yüzdesi yazılır. `target/` Git'e
  girmediği için kopyalamak şarttır.

**Örnek (`EnrollmentServiceTest.java`):**

```java
@Test
void courseWithOneFreeSeatHasCapacity() { // max-1
    Course course = new Course("SWE431", "Software Testing", 3, 2);
    course.addEnrolledStudent(new Student("202400001", "Ayse", true, 30));
    assertTrue(new EnrollmentService().hasCapacity(course));
}

@Test
void nullCourseThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> new EnrollmentService().hasCapacity(null));
}
```

### Üye 4: WithdrawalService + Regression Test + Contribution Record

> **Ödev bu bölümde ne istiyor:** **Regression testing**: bir değişikliğin
> çalışan kodu bozduğunda testlerin bunu yakaladığını kanıtlamak. Koda kasıtlı
> bir hata (defect) konur, test **fail** olur; hata düzeltilir, test **pass**
> olur ve iki çıktı da kayıt altına alınır. Ayrıca her üyenin katkısını
> gösteren **contribution record**.


- Öğrenci ders listesinden **numarasıyla** bulunarak çıkarılır. Listedeki
  nesne ile parametre olarak gelen nesne farklı olabilir; önce listedeki
  nesneyi bulun, onu `removeEnrolledStudent` ile silin.
- Başarılıysa `student.withdrawFromCourse(course.getCourseCode())` da
  çağrılır ve `true` döner. Öğrenci derse kayıtlı değilse hiçbir şey
  değişmeden `false` döner. Null argümanda `IllegalArgumentException`.
- Kayıt silmek bekleme listesinden öğrenci almaz; o iş
  `promoteNextStudent`'tadır.
- **Regression Test (`docs/regression.md`):** Kanıt adımları sırayla
  yapılır ve her adım ayrı commit olur:
  1. Testleri yazın, kodu doğru hâliyle tamamlayın → `mvn test` geçer.
  2. Koda **kasıtlı bir hata** koyun (örneğin
     `student.withdrawFromCourse(...)` satırını silin) → ilgili test
     **fail** olur. Çıktıyı kaydedin.
  3. Hatayı düzeltin → test tekrar **pass** olur. Çıktıyı kaydedin.
  `docs/regression.md` içine hatanın ne olduğu, hangi testin yakaladığı,
  fail ve pass çıktıları ile commit hash'leri yazılır.
  Bu PR **Squash and merge** ile değil, **Create a merge commit** ile
  birleştirilir; yoksa fail/pass commit'leri tek commit'e iner ve kanıt kaybolur.
- **Contribution Record (`docs/contribution-record.md`):** Her üyenin
  yaptığı işler, açtığı PR'lar ve commit'leri. Proje sonunda GitHub'daki
  **Insights → Contributors** ve PR listesiyle kontrol edilerek tamamlanır.

**Örnek (`WithdrawalServiceTest.java`):**

```java
@Test
void withdrawRemovesCourseFromStudent() {
    Course course = new Course("SWE431", "Software Testing", 3, 2);
    Student ali = new Student("202412345", "Ali", true, 30);
    course.addEnrolledStudent(ali);
    ali.enrollInCourse("SWE431");

    assertTrue(new WithdrawalService().withdraw(ali, course));
    assertFalse(ali.getEnrolledCourses().contains("SWE431"));
}
```

**Örnek (`docs/regression.md`):**

```markdown
## Regression 1
- Kasıtlı hata: `student.withdrawFromCourse(...)` satırı silindi (commit abc1234)
- Yakalayan test: `withdrawRemovesCourseFromStudent` → FAIL
- Düzeltme: satır geri eklendi (commit def5678) → PASS
```

### Üye 5 (lider): WaitlistService + CFG & Basis Paths + Entegrasyon

> **Ödev bu bölümde ne istiyor:** **White-box testing**: en karmaşık metodun
> (`promoteNextStudent`) **control-flow graph (CFG)**'ı çizilir, **siklomatik
> karmaşıklık** hesaplanır ve bu sayı kadar **basis path** (bağımsız yol)
> bulunur; her yol için bir test yazılır. Ayrıca servislerin birlikte doğru
> çalıştığını gösteren **integration test**.


- `addToWaitlist`, derste boş yer varsa ya da öğrenci zaten derste veya
  bekleme listesindeyse `REJECTED` döner. Bekleme listesine giren öğrencinin
  `enrolledCourses` kümesine ders eklenmez. Başarılıysa `WAITLISTED`.
- `promoteNextStudent` için:
  - Boş yer yoksa kuyruğa dokunmadan `null` döner.
  - Adaylar giriş sırasıyla kuyruktan çıkarılır. Uygun olmayan ya da zaten
    kayıtlı adaylar atlanır ve kuyruğa geri eklenmez.
  - İlk uygun aday Üye 3'teki gibi iki yerde de güncellenir ve döndürülür.
    Uygun aday yoksa `null` döner.
- Uygunluk kontrolü `Predicate` olarak geldiği için testlerde `s -> true` ve
  `s -> false` kullanılabilir. Bu testler EligibilityService'i beklemez.
- **CFG & Basis Paths (`docs/cfg-basis-paths.md`):** `promoteNextStudent`
  için kontrol akış grafiği çizilir, siklomatik karmaşıklık
  (V(G) = E − N + 2) hesaplanır, bağımsız yollar listelenir ve her yol için
  bir test yazılır.
- **Entegrasyon (`RegistrationSystemTest.java`):** Bütün servisler `main`'e
  girdikten sonra uçtan uca senaryolar: kayıt → ders dolunca bekleme
  listesi → kayıt silme → bekleme listesinden alma, geçersiz numarayla red.

**Örnek (`WaitlistServiceTest.java`):**

```java
@Test
void skipsIneligibleCandidateAndPromotesNext() {
    Course course = new Course("SWE431", "Software Testing", 3, 1); // 1 boş yer
    Student ayse = new Student("202400001", "Ayse", true, 30);
    Student ali = new Student("202400002", "Ali", true, 30);
    course.addToWaitlist(ayse);
    course.addToWaitlist(ali);

    Student promoted = new WaitlistService().promoteNextStudent(course, s -> s == ali);

    assertSame(ali, promoted);
    assertTrue(course.isEnrolled("202400002"));
    assertFalse(course.isWaitlisted("202400001")); // atlanan aday kuyruktan çıktı
}
```

**Örnek (`docs/cfg-basis-paths.md`):**

```markdown
V(G) = E - N + 2 = 4
P1: boş yer yok → null                       (noSeatReturnsNull)
P2: kuyruk boş → null                        (emptyWaitlistReturnsNull)
P3: ilk aday uygun → derse alınır            (promotesFirstEligible)
P4: ilk aday atlanır, ikinci alınır          (skipsIneligibleCandidateAndPromotesNext)
```

V(G) ve yollar kodu yazdıktan sonra gerçek grafiğe göre hesaplanır; yukarıdaki
sayı yalnızca biçim örneğidir.

## 5. Test yazma rehberi

Her test sınıfının başında ihtiyacınız olanları içe aktarın:

```java
import com.registration.model.Course;
import com.registration.model.EnrollmentStatus;
import com.registration.model.Student;
import com.registration.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
```

**Test verisini her testten önce hazırlayın:**

```java
private EnrollmentService service;
private Course course;
private Student ali;

@BeforeEach
void setUp() {
    service = new EnrollmentService();
    course = new Course("SWE431", "Software Testing", 3, 2); // kontenjan 2
    ali = new Student("202412345", "Ali", true, 30);
}
```

`src/test/java/com/registration/model/CourseTest.java` hazır bir örnektir.

**Sonucu ve yan etkiyi birlikte kontrol edin:**

```java
@Test
void enrollsStudentWhenSeatIsFree() {
    assertEquals(EnrollmentStatus.ENROLLED, service.enroll(ali, course));
    assertTrue(course.isEnrolled("202412345"));
    assertTrue(ali.getEnrolledCourses().contains("SWE431"));
}
```

**İstisna testi:**

```java
@Test
void rejectsNullStudent() {
    assertThrows(IllegalArgumentException.class, () -> service.enroll(null, course));
}
```

**"Hiçbir şey değişmedi" testi:**

```java
@Test
void secondEnrollmentIsRejectedWithoutChanges() {
    service.enroll(ali, course);
    Student sameId = new Student("202412345", "Ali", true, 30);

    assertEquals(EnrollmentStatus.REJECTED, service.enroll(sameId, course));
    assertEquals(1, course.getEnrolledStudents().size());
    assertTrue(sameId.getEnrolledCourses().isEmpty());
}
```

**Her metot için düşünülecek uç durumlar:** null argüman, boş metin, sınır
değerleri (tam dolu, tam bir eksik, kontenjan 0, kredi tam sınırda),
aynı numaralı farklı nesne, boş liste veya boş kuyruk.

**Her üye en az 5 test metodu yazar.** Her servis için önerilen başlangıç:

| Üye / Servis | Örnek 5 test |
|---|---|
| 1 Validation | geçerli numara, kısa/uzun numara, harfli numara, geçerli ders kodları, geçersiz ders kodları + null (hepsi parameterized) |
| 2 Eligibility | tüm koşullar sağlanıyor, pasif öğrenci, eksik ön koşul, kredi tam sınırda / sınırı aşıyor, null ve negatif kredi |
| 3 Enrollment | boş yerde kayıt, `max-1` / `max` / `max+1` sınırları, zaten kayıtlı/bekleyen öğrenci reddi, kontenjan 0, null argüman `assertThrows` |
| 4 Withdrawal | başarılı silme, kayıtlı olmayan öğrenci, aynı numaralı farklı nesne, öğrencinin ders kümesi güncelleniyor, null argüman |
| 5 Waitlist | doluyken listeye ekleme, boş yer varken red, tekrar ekleme reddi, sıradaki uygun öğrenciyi alma (basis path'ler), boş yer veya uygun aday yoksa `null` |

Test adlarını davranışı anlatacak şekilde verin:
`rejectsWhenCourseIsFull`, `returnsFalseForNullId` gibi.

Yalnızca kendi test sınıfınızı çalıştırmak için:

```sh
mvn "-Dtest=EnrollmentServiceTest" test
```

## 6. JaCoCo raporu

JaCoCo, testlerin kodun ne kadarını çalıştırdığını (kapsama, coverage)
ölçer. `mvn test` çalıştırınca `target/site/jacoco/index.html` raporu
oluşur. Dosyayı tarayıcıda açın ve kendi servisinize tıklayın:

- **Yeşil:** Testlerin çalıştırdığı satırlar.
- **Sarı:** Koşulun yalnızca bir tarafı test edilmiş (örneğin `if` test
  edilmiş ama `else` edilmemiş).
- **Kırmızı:** Hiçbir testin uğramadığı satırlar.

Sarı ve kırmızı satırlar için yeni test yazın. `target/` klasörü Git'e
eklenmez.

## 7. PR açmadan önce kontrol listesi

- [ ] Servis dosyanızda `TODO` veya `UnsupportedOperationException` kalmadı.
- [ ] `mvn test` → `BUILD SUCCESS`.
- [ ] JaCoCo'da kendi servisinizin satır kapsaması en az **%80**, dallar da
      mümkün olduğunca yeşil.
- [ ] Null, sınır ve "değişmedi" testleri var.
- [ ] Kendi test sınıfınızda **en az 5 test metodu** var. Hazır gelen
      `facadeProvidesService` testi sayılmaz. Projede toplam en az 20 test
      olmalı.
- [ ] Yalnızca kendi servis, test ve `docs/` dosyalarınız değişti (`git status` ile bakın).
- [ ] Rol tablosundaki ek teslim dokümanınız `docs/` altında.
- [ ] PR açıklamasında neyi test ettiğiniz yazıyor.

Takıldığınız yerde Javadoc'u tekrar okuyun, sonra gruba yazın.

## Not: `docs/` klasörü hakkında

`docs/` klasörü ve içindeki dosya adları (`decision-table.md`,
`coverage/` vb.) ödevin şartı değil, kod dışı teslimleri tek yerde toplamak
için önerilen bir düzendir. `docs/coverage/` ayrıca gereklidir çünkü JaCoCo
raporu `target/` içinde oluşur ve `target/` Git'e eklenmez. Dokümanlar ayrı
bir rapor olarak teslim edilecekse bu klasör kullanılmayabilir.
