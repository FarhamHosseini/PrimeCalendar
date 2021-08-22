package com.apachat.primecalendar.core.common

import com.apachat.primecalendar.core.PrimeCalendar
import com.apachat.primecalendar.core.civil.CivilCalendar
import com.apachat.primecalendar.core.hijri.HijriCalendar
import com.apachat.primecalendar.core.japanese.JapaneseCalendar
import com.apachat.primecalendar.core.persian.PersianCalendar
import java.util.*

@Suppress("unused")
object CalendarFactory {

  @JvmStatic
  fun <T : PrimeCalendar> newInstance(clazz: Class<T>): T =
    clazz.getDeclaredConstructor().newInstance()

  @JvmStatic
  fun newInstance(type: CalendarType): PrimeCalendar {
    return when (type) {
      CalendarType.CIVIL -> CivilCalendar()
      CalendarType.PERSIAN -> PersianCalendar()
      CalendarType.HIJRI -> HijriCalendar()
      CalendarType.JAPANESE -> JapaneseCalendar()
    }
  }

  @JvmStatic
  fun newInstance(type: CalendarType, locale: Locale): PrimeCalendar {
    return when (type) {
      CalendarType.CIVIL -> CivilCalendar(locale = locale)
      CalendarType.PERSIAN -> PersianCalendar(locale = locale)
      CalendarType.HIJRI -> HijriCalendar(locale = locale)
      CalendarType.JAPANESE -> JapaneseCalendar(locale = locale)
    }
  }

  @JvmStatic
  fun newInstance(type: CalendarType, timeZone: TimeZone): PrimeCalendar {
    return when (type) {
      CalendarType.CIVIL -> CivilCalendar(timeZone)
      CalendarType.PERSIAN -> PersianCalendar(timeZone)
      CalendarType.HIJRI -> HijriCalendar(timeZone)
      CalendarType.JAPANESE -> JapaneseCalendar(timeZone)
    }
  }

  @JvmStatic
  fun newInstance(type: CalendarType, timeZone: TimeZone, locale: Locale): PrimeCalendar {
    return when (type) {
      CalendarType.CIVIL -> CivilCalendar(timeZone, locale)
      CalendarType.PERSIAN -> PersianCalendar(timeZone, locale)
      CalendarType.HIJRI -> HijriCalendar(timeZone, locale)
      CalendarType.JAPANESE -> JapaneseCalendar(timeZone, locale)
    }
  }

}
