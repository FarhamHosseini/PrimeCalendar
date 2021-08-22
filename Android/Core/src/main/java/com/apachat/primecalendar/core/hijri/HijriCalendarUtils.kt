package com.apachat.primecalendar.core.hijri

import com.apachat.primecalendar.core.common.DateHolder
import com.apachat.primecalendar.core.persian.PersianCalendar.Companion.DEFAULT_LOCALE
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.HijrahDate
import org.threeten.bp.temporal.ChronoField
import java.util.*
import java.util.Calendar.*

@Suppress("SpellCheckingInspection")
object HijriCalendarUtils {
  private val normalMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29)
  private val leapYearMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30)

  private val normalMonthLengthAggregated =
    intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 354)
  private val leapYearMonthLengthAggregated =
    intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 355)

  internal val monthNames = arrayOf(
    "محرم",
    "صفر",
    "ربيع الأول",
    "ربيع الثاني",
    "جمادى الأولى",
    "جمادى الآخرة",
    "رجب",
    "شعبان",
    "رمضان",
    "شوال",
    "ذو القعدة",
    "ذو الحجة"
  )

  internal val weekDays = arrayOf(
    "السبت",
    "الأحد",
    "الإثنين",
    "الثلاثاء",
    "الأربعاء",
    "الخميس",
    "الجمعة"
  )

  internal val eras = arrayOf(
    "بعد الميلاد",
    "قبل الميلاد"
  )

  internal val amPm = arrayOf(
    "قبل الظهر",
    "بعد الظهر"
  )

  internal val shortMonthNames = arrayOf(
    "مح",
    "صف",
    "رب١",
    "رب٢",
    "جم١",
    "جم٢",
    "رج",
    "شع",
    "رم",
    "شو",
    "ذقع",
    "ذحج"
  )

  internal val shortWeekDays = arrayOf(
    "سب",
    "أح",
    "إث",
    "ثل",
    "أر",
    "خم",
    "جم"
  )

  internal val monthNamesEn = arrayOf(
    "Muharram",
    "Safar",
    "Rabiʿ al-Awwal",
    "Rabiʿ ath-Thani",
    "Jumada al-Ula",
    "Jumada al-Akhirah",
    "Rajab",
    "Sha'ban",
    "Ramadan",
    "Shawwal",
    "Dhu al-Qa'dah",
    "Dhu al-Hijjah"
  )

  internal val weekDaysEn = arrayOf(
    "Saturday",
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday"
  )

  internal val erasEn = arrayOf(
    "AD",
    "BC"
  )

  internal val amPmEn = arrayOf(
    "AM",
    "PM"
  )

  internal val shortMonthNamesEn = arrayOf(
    "Muh",
    "Saf",
    "Ra1",
    "Ra2",
    "Ja1",
    "Ja2",
    "Raj",
    "Shb",
    "Ram",
    "Shw",
    "DQa",
    "DHj"
  )

  internal val shortWeekDaysEn = arrayOf(
    "Sa",
    "Su",
    "Mo",
    "Tu",
    "We",
    "Th",
    "Fr"
  )

  fun monthName(month: Int, locale: Locale): String {
    return when (locale.language) {
      DEFAULT_LOCALE -> monthNames[month]
      else -> monthNamesEn[month]
    }
  }

  fun shortMonthName(month: Int, locale: Locale): String {
    return when (locale.language) {
      DEFAULT_LOCALE -> shortMonthNames[month]
      else -> shortMonthNamesEn[month]
    }
  }

  fun weekDayName(weekDay: Int, locale: Locale): String {
    val array = when (locale.language) {
      DEFAULT_LOCALE -> weekDays
      else -> weekDaysEn
    }
    return when (weekDay) {
      SATURDAY -> array[0]
      SUNDAY -> array[1]
      MONDAY -> array[2]
      TUESDAY -> array[3]
      WEDNESDAY -> array[4]
      THURSDAY -> array[5]
      FRIDAY -> array[6]
      else -> throw IllegalArgumentException()
    }
  }

  fun shortWeekDayName(weekDay: Int, locale: Locale): String {
    val array = when (locale.language) {
      DEFAULT_LOCALE -> shortWeekDays
      else -> shortWeekDaysEn
    }
    return when (weekDay) {
      SATURDAY -> array[0]
      SUNDAY -> array[1]
      MONDAY -> array[2]
      TUESDAY -> array[3]
      WEDNESDAY -> array[4]
      THURSDAY -> array[5]
      FRIDAY -> array[6]
      else -> throw IllegalArgumentException()
    }
  }

  fun monthLength(year: Int, month: Int): Int =
    if (isHijriLeapYear(year))
      leapYearMonthLength[month]
    else normalMonthLength[month]

  fun yearLength(year: Int): Int =
    if (isHijriLeapYear(year))
      leapYearMonthLengthAggregated[12]
    else normalMonthLengthAggregated[12]

  internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
    if (isHijriLeapYear(year))
      leapYearMonthLengthAggregated[month] + dayOfMonth
    else normalMonthLengthAggregated[month] + dayOfMonth

  internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
    val monthLengthAggregated = if (isHijriLeapYear(year))
      leapYearMonthLengthAggregated
    else normalMonthLengthAggregated

    var month = 0
    for (i in monthLengthAggregated.indices) {
      if (dayOfYear > monthLengthAggregated[i] && dayOfYear <= monthLengthAggregated[i + 1]) {
        month = i
      }
    }
    val dayOfMonth = dayOfYear - monthLengthAggregated[month]
    return DateHolder(year, month, dayOfMonth)
  }

  internal fun isHijriLeapYear(year: Int): Boolean =
    (14 + 11 * if (year > 0) year else -year) % 30 < 11

  internal fun gregorianToHijri(gregorian: DateHolder): DateHolder {
    val gregorianDate = LocalDate.of(gregorian.year, gregorian.month + 1, gregorian.dayOfMonth)
    val hijriDate = HijrahDate.from(gregorianDate)
    return DateHolder(
      hijriDate.get(ChronoField.YEAR),
      hijriDate.get(ChronoField.MONTH_OF_YEAR) - 1,
      hijriDate.get(ChronoField.DAY_OF_MONTH)
    )
  }

  internal fun hijriToGregorian(hijri: DateHolder): DateHolder {
    val hijriDate = HijrahDate.of(hijri.year, hijri.month + 1, hijri.dayOfMonth)
    val gregorianDate = LocalDate.from(hijriDate)
    return DateHolder(
      gregorianDate.get(ChronoField.YEAR),
      gregorianDate.get(ChronoField.MONTH_OF_YEAR) - 1,
      gregorianDate.get(ChronoField.DAY_OF_MONTH)
    )
  }

}