package com.apachat.primecalendar.core.common

import com.apachat.primecalendar.core.PrimeCalendar
import com.apachat.primecalendar.core.civil.CivilCalendar
import com.apachat.primecalendar.core.hijri.HijriCalendar
import com.apachat.primecalendar.core.japanese.JapaneseCalendar
import com.apachat.primecalendar.core.persian.PersianCalendar
import java.util.*

fun Calendar.toCivil(): CivilCalendar =
  CivilCalendar(timeZone).also { it.adjustWith(this) }

fun Calendar.toPersian(): PersianCalendar =
  PersianCalendar(timeZone).also { it.adjustWith(this) }

fun Calendar.toHijri(): HijriCalendar =
  HijriCalendar(timeZone).also { it.adjustWith(this) }

fun Calendar.toJapanese(): JapaneseCalendar =
  JapaneseCalendar(timeZone).also { it.adjustWith(this) }

fun Calendar.toPrimeCalendar(calendarType: CalendarType): PrimeCalendar =
  CalendarFactory.newInstance(calendarType, timeZone).also { it.adjustWith(this) }