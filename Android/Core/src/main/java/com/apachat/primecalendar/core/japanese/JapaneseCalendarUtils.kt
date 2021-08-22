package com.apachat.primecalendar.core.japanese

import com.apachat.primecalendar.core.common.DateHolder
import com.apachat.primecalendar.core.persian.PersianCalendar.Companion.DEFAULT_LOCALE
import java.util.*
import java.util.Calendar.*

@Suppress("SpellCheckingInspection")
object JapaneseCalendarUtils {
  private val normalMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  private val leapYearMonthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

  private val normalMonthLengthAggregated =
    intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365)
  private val leapYearMonthLengthAggregated =
    intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366)

  internal val monthNames = arrayOf(
    "いち がつ",
    "に がつ",
    "さん がつ",
    "し がつ",
    "ご がつ",
    "ろく がつ",
    "しち がつ",
    "はち がつ",
    "く がつ",
    "じゅう がつ",
    "じゅういち がつ",
    "じゅうに がつ"
  )

  internal val weekDays = arrayOf(
    "ど ようび",
    "にち ようび",
    "げつ ようび",
    "か ようび",
    "すい ようび",
    "もく ようび",
    "きん ようび"
  )

  internal val eras = arrayOf(
    "せいれき",
    "きげんぜん"
  )

  internal val amPm = arrayOf(
    "ごぜん",
    "ごご"
  )

  internal val shortMonthNames = arrayOf(
    "一月",
    "二月",
    "三月",
    "四月",
    "五月",
    "六月",
    "七月",
    "八月",
    "九月",
    "十月",
    "十一月",
    "十二月"
  )

  internal val shortWeekDays = arrayOf(
    "ど",
    "にち",
    "げつ",
    "か",
    "すい",
    "もく",
    "きん"
  )

  internal val monthNamesEn = arrayOf(
    "Ichigatsu",
    "Nigatsu",
    "Sangatsu",
    "Shigatsu",
    "Gogatsu",
    "Rokugatsu",
    "Shichigatsu",
    "Hachigatsu",
    "Kugatsu",
    "Jūgatsu",
    "Jūichigatsu",
    "Jūnigatsu"
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
    "Ichi",
    "Ni",
    "San",
    "Shi",
    "Go",
    "Roku",
    "Shichi",
    "Hachi",
    "Ku",
    "Jū",
    "Jūichi",
    "Jūni"
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
    if (isJapaneseLeapYear(year))
      leapYearMonthLength[month]
    else normalMonthLength[month]

  fun yearLength(year: Int): Int =
    if (isJapaneseLeapYear(year))
      leapYearMonthLengthAggregated[12]
    else normalMonthLengthAggregated[12]

  internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
    if (isJapaneseLeapYear(year))
      leapYearMonthLengthAggregated[month] + dayOfMonth
    else normalMonthLengthAggregated[month] + dayOfMonth

  internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
    val monthLengthAggregated = if (isJapaneseLeapYear(year))
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

  internal fun isJapaneseLeapYear(year: Int): Boolean =
    ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

  internal fun gregorianToJapanese(gregorian: DateHolder): DateHolder {
    return gregorian
//        val gregorianDate = LocalDate.of(gregorian.year, gregorian.month + 1, gregorian.dayOfMonth)
//        val japaneseDate = JapaneseDate.from(gregorianDate)
//        return DateHolder(japaneseDate.get(ChronoField.YEAR), japaneseDate.get(ChronoField.MONTH_OF_YEAR) - 1, japaneseDate.get(ChronoField.DAY_OF_MONTH))
  }

  internal fun japaneseToGregorian(japanese: DateHolder): DateHolder {
    return japanese
//        val japaneseDate = JapaneseDate.of(japanese.year, japanese.month + 1, japanese.dayOfMonth)
//        val gregorianDate = LocalDate.from(japaneseDate)
//        return DateHolder(gregorianDate.get(ChronoField.YEAR), gregorianDate.get(ChronoField.MONTH_OF_YEAR) - 1, gregorianDate.get(ChronoField.DAY_OF_MONTH))
  }

}