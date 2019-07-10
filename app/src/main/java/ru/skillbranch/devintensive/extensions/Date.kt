package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value : Int, unit : TimeUnits = TimeUnits.SECOND) : Date {
    this.time += value * getTimeFactorByTimeUnit(unit)
    return this
}

fun Date.humanizeDiff(date : Date = Date()) : String {
    val difference : Long
    val isFuture : Boolean
    if (this.after(date)) {
        difference = ((this.time - date.time) / SECOND) * SECOND
        isFuture = true
    } else {
        difference = ((date.time - this.time) / SECOND) * SECOND
        isFuture = false
    }
    return when (difference) {
        in 0L .. 1L * SECOND -> "только что"
        in 1L * SECOND .. 45L * SECOND -> if (isFuture) "через несколько секунд" else "несколько секунд назад"
        in 45L * SECOND .. 75L * SECOND -> if (isFuture) "через минуту" else "минуту назад"
        in 75L * SECOND .. 45L * MINUTE  -> {
            if (isFuture) "через " + humanizeTimeDifference(difference, TimeUnits.MINUTE)
            else humanizeTimeDifference(difference, TimeUnits.MINUTE) + " назад"
        }
        in 45L * MINUTE .. 75L * MINUTE  -> if (isFuture) "через час" else "час назад"
        in 75L * MINUTE .. 22L * HOUR  -> {
            if (isFuture) "через " + humanizeTimeDifference(difference, TimeUnits.HOUR)
            else humanizeTimeDifference(difference, TimeUnits.HOUR) + " назад"
        }
        in 22L * HOUR .. 26L * HOUR  -> if (isFuture) "через день" else "день назад"
        in 26L * HOUR .. 360L * DAY  -> {
            if (isFuture) "через " + humanizeTimeDifference(difference, TimeUnits.DAY)
            else humanizeTimeDifference(difference, TimeUnits.DAY) + " назад"
        }
        else -> if (isFuture) "более чем через год" else "более года назад"
    }
}

private fun getTimeFactorByTimeUnit(unit: TimeUnits) : Long {
    return when (unit) {
        TimeUnits.SECOND -> SECOND
        TimeUnits.MINUTE -> MINUTE
        TimeUnits.HOUR -> HOUR
        TimeUnits.DAY -> DAY
    }
}

private fun humanizeTimeDifference(difference : Long, timeunit : TimeUnits) : String {
    val description : String
    val number = difference / getTimeFactorByTimeUnit(timeunit)
    val remainder10 : Long = number % 10L
    val remainder100 : Long = number % 100L
    when (timeunit) {
        TimeUnits.SECOND -> {
            description = when (remainder100) {
                in 10L..20L -> "секунд"
                else -> when (remainder10) {
                    1L -> "секунду"
                    2L, 3L, 4L -> "секунды"
                    else -> "секунд"
                }
            }
        }
        TimeUnits.MINUTE -> {
            description = when (remainder100) {
                in 10L..20L -> "минут"
                else -> when (remainder10) {
                    1L -> "минуту"
                    2L, 3L, 4L -> "минуты"
                    else -> "минут"
                }
            }
        }
        TimeUnits.HOUR -> {
            description = when (remainder100) {
                in 10L..20L -> "часов"
                else -> when (remainder10) {
                    1L -> "час"
                    2L, 3L, 4L -> "часа"
                    else -> "часов"
                }
            }
        }
        TimeUnits.DAY -> {
            description = when (remainder100) {
                in 10L..20L -> "дней"
                else -> when (remainder10) {
                    1L -> "день"
                    2L, 3L, 4L -> "дня"
                    else -> "дней"
                }
            }
        }
    }
    return "$number $description"
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value : Int): String {
        return humanizeTimeDifference(value.toLong() * getTimeFactorByTimeUnit(this), this)
    }
}