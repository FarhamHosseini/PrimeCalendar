# `PrimeCalendar` :zap:

**`PrimeCalendar`** provides all the [`java.util.Calendar`](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) functionalities for `Persian`, `Hijri`, and `Japanese` dates.
**`PrimeCalendar`** can be used in every **JVM-based** projects such as **Java/kotlin** applications, **Android** apps, *etc*.  

:bell: This library has been created from the [Aminography](https://github.com/aminography/PrimeCalendar) repository and has been rewritten just to be included in `mavenCentral()`

This library contains three types of calendar systems as well as their conversion to each other.

  | Calendar System | Provider Class | Descriptions |
  | --- | --- | --- |
  |[Iranian](https://en.wikipedia.org/wiki/Iranian_calendars)| [PersianCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/persian/PersianCalendar.kt) | The most accurate solar calendar in use today. |
  |[Islamic](https://en.wikipedia.org/wiki/Islamic_calendar)| [HijriCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/hijri/HijriCalendar.kt) | A lunar calendar consisting of 12 lunar months in a year of 354 or 355 days. |
  |[Gregorian](https://en.wikipedia.org/wiki/Gregorian_calendar)| [CivilCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/civil/CivilCalendar.kt) | The common calendar which is used in most of the world. |
  |[Japanese](https://en.wikipedia.org/wiki/Japanese_calendar)| [JapaneseCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/japanese/JapaneseCalendar.kt) | The calendar which is used in Japan. |

## Usage
#### Set up the dependency

### • Gradle
1. Add the mavenCentral() repository to your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		mavenCentral()
	}
}
```
2. Add the PrimeCalendar dependency in the build.gradle:
```
implementation group: 'com.apachat', name: 'primecalendar-android', version: '1.3.93'
```

### • Maven
```xml
<dependencies>
  <dependency>
    <groupId>com.apachat</groupId>
    <artifactId>primecalendar-android</artifactId>
    <version>1.3.93</version>
  </dependency>
</dependencies>
```

Badge:
-----
[![Maven Central](https://img.shields.io/maven-central/v/com.apachat/primecalendar-android.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.apachat%22%20AND%20a:%22primecalendar-android%22)

Calendar objects can be instantiated by the class constructors or using [`CalendarFactory`](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/common/CalendarFactory.kt).

> **Java**
```java
PrimeCalendar calendar = new PersianCalendar();
// or
PrimeCalendar calendar = CalendarFactory.newInstance(CalendarType.PERSIAN);
```

> **Kotlin**
```kotlin
val calendar = HijriCalendar()
// or
val calendar = CalendarFactory.newInstance(CalendarType.HIJRI)
```

### • Functionalities
Exactly all of the standard `Calendar` functionalities are implemented in **`PrimeCalendar`** including `set`, `add`, `roll`, *etc*.

```kotlin
val civil = CivilCalendar()
civil.set(2019, 5, 17)
println(civil.longDateString)

civil.set(Calendar.DAY_OF_YEAR, 192)
println(civil.longDateString)

civil.add(Calendar.WEEK_OF_YEAR, 14)
println(civil.longDateString)

civil.roll(Calendar.DAY_OF_WEEK, -3)
println(civil.longDateString)

---------------------------
> Monday, 17 June 2019
> Thursday, 11 July 2019
> Thursday, 17 October 2019
> Monday, 14 October 2019
```

### • Date Conversion
Conversion of dates to each other is simply possible by calling the converter methods.

```kotlin
// Converting calendar instance to PersianCalendar:
val persian = calendar.toPersian()

// Converting calendar instance to HijriCalendar:
val hijri = calendar.toHijri()

// Converting calendar instance to CivilCalendar:
val civil = calendar.toCivil()

// Converting calendar instance to JapaneseCalendar:
val japanese = calendar.toJapanese()
```

Also, it is possible to convert an instance of `java.util.Calendar` to an instance of `PrimeCalendar`. For example:
```kotlin
import java.util.Calendar

val calendar = Calendar.getInstance()

// Converting to PersianCalendar:
val persian = calendar.toPersian()
```

### • Kotlin Operators
There is a different way to use `get`, `set`, and `add` methods. Using operators you can do it much simpler.
Suppose that the `calendar` is an instance of `PrimeCalendar`:

> get
```kotlin
val year = calendar.get(Calendar.YEAR)

// equivalent operations:
val year = calendar[Calendar.YEAR]
val year = calendar.year
```

> set
```kotlin
calendar.set(Calendar.MONTH, 7)

// equivalent operations:
calendar[Calendar.MONTH] = 7
calendar.set(Month(7))
calendar.set(7.month)
calendar.month = 7
```

> add
```kotlin
calendar.add(Calendar.DAY_OF_MONTH, 27)

// equivalent operations:
calendar[Calendar.DAY_OF_MONTH] += 27
calendar += DayOfMonth(27)
calendar += 27.dayOfMonth
```

### • Localization
You can localize digits, month names, and week day names by passing locale in constructor. For Persian and Hijri calendars, the default locale is set to Farsi and Arabic respectively.

```kotlin
val persian = PersianCalendar()
println(persian.longDateString)

---------------------------
> پنج‌شنبه، ۲۳ خرداد ۱۳۹۸
```

```kotlin
val persian = PersianCalendar(Locale.ENGLISH)
println(persian.longDateString)

---------------------------
> Thursday, 23 Khordad 1398
```

Third Party Libraries
---------------------
**• [ThreeTen-Backport](https://www.threeten.org/threetenbp)**

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/FarhamHosseini/PrimeCalendar/issues).
