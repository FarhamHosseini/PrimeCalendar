package com.apachat.primecalendar.core

import com.apachat.primecalendar.core.civil.CivilCalendar
import com.apachat.primecalendar.core.common.*
import com.apachat.primecalendar.core.common.operators.CalendarField
import com.apachat.primecalendar.core.hijri.HijriCalendar
import com.apachat.primecalendar.core.japanese.JapaneseCalendar
import com.apachat.primecalendar.core.persian.PersianCalendar
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class PrimeCalendar(
  timeZone: TimeZone,
  val locale: Locale
) : Comparable<PrimeCalendar> {
  protected var internalCalendar = GregorianCalendar(timeZone, locale)

  protected var internalYear: Int = 0

  protected var internalMonth: Int = 0

  protected var internalDayOfMonth: Int = 0

  var year: Int
    get() = internalYear
    set(value) = set(value, internalMonth, internalDayOfMonth)

  var month: Int
    get() = internalMonth
    set(value) = set(internalYear, value, internalDayOfMonth)

  var weekOfYear: Int
    get() = get(WEEK_OF_YEAR)
    set(value) = set(WEEK_OF_YEAR, value)

  var weekOfMonth: Int
    get() = get(WEEK_OF_MONTH)
    set(value) = set(WEEK_OF_MONTH, value)

  var date: Int
    get() = internalDayOfMonth
    set(value) = set(internalYear, internalMonth, value)

  var dayOfMonth: Int
    get() = internalDayOfMonth
    set(value) = set(internalYear, internalMonth, value)

  var dayOfYear: Int
    get() = get(DAY_OF_YEAR)
    set(value) = set(DAY_OF_YEAR, value)

  var dayOfWeek: Int
    get() = get(DAY_OF_WEEK)
    set(value) = set(DAY_OF_WEEK, value)

  var dayOfWeekInMonth: Int
    get() = get(DAY_OF_WEEK_IN_MONTH)
    set(value) = set(DAY_OF_WEEK_IN_MONTH, value)

  var hour: Int
    get() = get(HOUR)
    set(value) = set(HOUR, value)

  var hourOfDay: Int
    get() = get(HOUR_OF_DAY)
    set(value) = set(HOUR_OF_DAY, value)

  var minute: Int
    get() = get(MINUTE)
    set(value) = set(MINUTE, value)

  var second: Int
    get() = get(SECOND)
    set(value) = set(SECOND, value)

  var millisecond: Int
    get() = get(MILLISECOND)
    set(value) = set(MILLISECOND, value)

  abstract val monthName: String

  abstract val monthNameShort: String

  abstract val weekDayName: String

  abstract val weekDayNameShort: String

  abstract val monthLength: Int

  abstract val isLeapYear: Boolean

  abstract var firstDayOfWeek: Int

  abstract val calendarType: CalendarType

  val longDateString: String
    get() = weekDayName +
      "${comma(locale)} " +
      "${dayOfMonth.localizeDigits(locale)} " +
      "$monthName " +
      year.localizeDigits(locale)

  val shortDateString: String
    get() = normalize(locale, year) +
      delimiter +
      normalize(locale, month + 1) +
      delimiter +
      normalize(locale, dayOfMonth)

  val monthDayString: String
    get() = "$monthName ${normalize(locale, dayOfMonth)}"

  open operator fun get(field: Int): Int {
    return internalCalendar.get(field)
  }

  open fun set(calendarField: CalendarField) {
    internalCalendar.set(calendarField.field, calendarField.amount)
  }

  open operator fun set(field: Int, value: Int) {
    internalCalendar.set(field, value)
  }

  open fun set(year: Int, month: Int, dayOfMonth: Int) {
    internalCalendar.set(year, month, dayOfMonth)
  }

  open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
    internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute)
  }

  open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
    internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute, second)
  }

  open fun add(field: Int, amount: Int) {
    internalCalendar.add(field, amount)
  }

  open fun roll(field: Int, amount: Int) {
    internalCalendar.roll(field, amount)
  }

  open fun getMinimum(field: Int): Int {
    return internalCalendar.getMinimum(field)
  }

  open fun getMaximum(field: Int): Int {
    return internalCalendar.getMaximum(field)
  }

  open fun getGreatestMinimum(field: Int): Int {
    return internalCalendar.getGreatestMinimum(field)
  }

  open fun getLeastMaximum(field: Int): Int {
    return internalCalendar.getLeastMaximum(field)
  }

  open fun getActualMinimum(field: Int): Int {
    return internalCalendar.getActualMinimum(field)
  }

  open fun getActualMaximum(field: Int): Int {
    return internalCalendar.getActualMaximum(field)
  }

  fun roll(field: Int, up: Boolean) {
    roll(field, if (up) +1 else -1)
  }

  fun setTimeZone(zone: TimeZone) {
    internalCalendar.timeZone = zone
    invalidate()
  }

  fun getTimeZone(): TimeZone {
    return internalCalendar.timeZone
  }

  var timeInMillis: Long = 0
    get() = internalCalendar.timeInMillis
    set(value) {
      field = value
      internalCalendar.timeInMillis = value
      invalidate()
    }

  fun toCivil(): CivilCalendar =
    CivilCalendar().also { it.timeInMillis = timeInMillis }

  fun toPersian(): PersianCalendar =
    PersianCalendar().also { it.timeInMillis = timeInMillis }

  fun toHijri(): HijriCalendar =
    HijriCalendar().also { it.timeInMillis = timeInMillis }

  fun toJapanese(): JapaneseCalendar =
    JapaneseCalendar().also { it.timeInMillis = timeInMillis }

  protected abstract fun store()

  protected abstract fun invalidate()

  protected abstract fun configSymbols(symbols: DateFormatSymbols)

  internal abstract fun monthLength(year: Int, month: Int): Int

  internal abstract fun yearLength(year: Int): Int

  internal abstract fun dayOfYear(): Int

  internal abstract fun dayOfYear(year: Int, dayOfYear: Int): DateHolder

  protected fun setInternalFirstDayOfWeek(firstDayOfWeek: Int) {
    internalCalendar.firstDayOfWeek = firstDayOfWeek
  }

  internal fun weekOfMonth(): Int {
    return CalendarFactory.newInstance(calendarType).let { base ->
      base.set(year, month, 1)
      val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
      weekNumber(dayOfMonth, baseDayOfWeek)
    }
  }

  internal fun weekOfYear(): Int {
    return CalendarFactory.newInstance(calendarType).let { base ->
      base.set(year, 0, 1)
      val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
      weekNumber(dayOfYear(), baseDayOfWeek)
    }
  }

  protected fun adjustDayOfWeekOffset(dayOfWeek: Int): Int {
    val day = if (dayOfWeek < firstDayOfWeek) dayOfWeek + 7 else dayOfWeek
    return (day - firstDayOfWeek) % 7
  }

  private fun weekNumber(day: Int, baseOffset: Int): Int {
    val dividend = (baseOffset + day) / 7
    val remainder = (baseOffset + day) % 7
    return dividend + if (remainder > 0) 1 else 0
  }

  internal fun adjustWith(calendar: Calendar) {
    internalCalendar.timeInMillis = calendar.timeInMillis
    firstDayOfWeek = calendar.firstDayOfWeek
    invalidate()
  }

  fun getTime(): Date {
    return Date(timeInMillis)
  }

  fun setTime(date: Date) {
    timeInMillis = date.time
  }

  fun clear() {
    internalCalendar.clear()
    invalidate()
  }

  fun clear(field: Int) {
    internalCalendar.clear(field)
    invalidate()
  }

  fun isSet(field: Int): Boolean {
    return internalCalendar.isSet(field)
  }

  fun getDisplayName(field: Int, style: Int, locale: Locale): String? {
    if (!checkDisplayNameParams(
        field,
        style,
        ALL_STYLES,
        LONG,
        locale,
        ERA,
        MONTH,
        DAY_OF_WEEK,
        AM_PM
      )
    ) {
      return null
    }
    val symbols = DateFormatSymbols.getInstance(locale)
    configSymbols(symbols)

    return getFieldStrings(field, style, symbols)?.let {
      val fieldValue = get(field)
      if (fieldValue < it.size) it[fieldValue]
      else null
    }
  }

  fun getDisplayNames(field: Int, style: Int, locale: Locale): Map<String, Int>? {
    if (!checkDisplayNameParams(
        field,
        style,
        ALL_STYLES,
        LONG,
        locale,
        ERA,
        MONTH,
        DAY_OF_WEEK,
        AM_PM
      )
    ) {
      return null
    }
    if (style == ALL_STYLES) {
      val shortNames = getDisplayNamesImpl(field, SHORT, locale)
      if (field == ERA || field == AM_PM) {
        return shortNames
      }
      val longNames = getDisplayNamesImpl(field, LONG, locale)
      if (shortNames == null) {
        return longNames
      }
      if (longNames != null) {
        shortNames.putAll(longNames)
      }
      return shortNames
    }
    return getDisplayNamesImpl(field, style, locale)
  }

  private fun getDisplayNamesImpl(
    field: Int,
    style: Int,
    locale: Locale
  ): MutableMap<String, Int>? {
    val symbols = DateFormatSymbols.getInstance(locale)
    configSymbols(symbols)

    return getFieldStrings(field, style, symbols)?.let {
      HashMap<String, Int>().also { names ->
        for (i in it.indices) {
          if (it[i].isEmpty()) continue
          names[it[i]] = i
        }
      }
    }
  }

  private fun checkDisplayNameParams(
    field: Int,
    style: Int,
    minStyle: Int,
    maxStyle: Int,
    locale: Locale?,
    vararg fields: Int
  ): Boolean {
    if (field < 0 || field >= FIELD_COUNT || style < minStyle || style > maxStyle) {
      throw IllegalArgumentException()
    }
    if (locale == null) {
      throw NullPointerException()
    }
    return isFieldSet(fields, field)
  }

  private fun isFieldSet(fields: IntArray, field: Int): Boolean {
    return fields.contains(field)
  }

  private fun getFieldStrings(field: Int, style: Int, symbols: DateFormatSymbols): Array<String>? {
    return when (field) {
      ERA -> symbols.eras
      MONTH -> if (style == LONG) symbols.months else symbols.shortMonths
      DAY_OF_WEEK -> if (style == LONG) symbols.weekdays else symbols.shortWeekdays
      AM_PM -> symbols.amPmStrings
      else -> null
    }
  }

  fun before(whenCalendar: Any): Boolean {
    return whenCalendar is PrimeCalendar && compareTo(whenCalendar) < 0
  }

  fun after(whenCalendar: Any): Boolean {
    return whenCalendar is PrimeCalendar && compareTo(whenCalendar) > 0
  }

  override operator fun compareTo(other: PrimeCalendar): Int {
    return compareTo(other.timeInMillis)
  }

  private operator fun compareTo(otherTime: Long): Int {
    val thisTime = timeInMillis
    return if (thisTime > otherTime) 1 else if (thisTime == otherTime) 0 else -1
  }

  fun clone(): PrimeCalendar {
    return CalendarFactory.newInstance(calendarType, internalCalendar.timeZone, locale).also {
      it.internalCalendar = internalCalendar.clone() as GregorianCalendar
      it.firstDayOfWeek = firstDayOfWeek
      it.invalidate()
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    } else if (other is PrimeCalendar) {
      return internalCalendar == other.internalCalendar
    }
    return false
  }

  override fun hashCode(): Int {
    return internalCalendar.hashCode()
  }

  override fun toString(): String {
    return super.toString().apply {
      "${substring(0, length - 1)}, Date=$shortDateString]"
    }
  }

  companion object {
    private val FIELD_NAME = arrayOf(
      "ERA",
      "YEAR",
      "MONTH",
      "WEEK_OF_YEAR",
      "WEEK_OF_MONTH",
      "DAY_OF_MONTH",
      "DAY_OF_YEAR",
      "DAY_OF_WEEK",
      "DAY_OF_WEEK_IN_MONTH",
      "AM_PM",
      "HOUR",
      "HOUR_OF_DAY",
      "MINUTE",
      "SECOND",
      "MILLISECOND",
      "ZONE_OFFSET",
      "DST_OFFSET"
    )

    fun fieldName(field: Int): String {
      return FIELD_NAME[field]
    }
  }
}
