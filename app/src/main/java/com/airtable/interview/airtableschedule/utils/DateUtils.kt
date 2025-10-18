package com.airtable.interview.airtableschedule.utils

import com.airtable.interview.airtableschedule.models.Event
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import kotlin.math.abs

fun daysBetweenAbsolute(start: Date?, end: Date?): Long? {
    return try {
        if (start == null || end == null) return null
        val zone = ZoneId.systemDefault()
        val startLocal = start.toInstant()?.atZone(zone)?.toLocalDate()
        val endLocal = end.toInstant()?.atZone(zone)?.toLocalDate()
        abs(ChronoUnit.DAYS.between(startLocal, endLocal))
    } catch (ex: Exception) {
        null
    }
}

fun absoluteDay(date: Date?): Int? {
    return try {
        if (date == null) return null
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val mes = localDate.monthValue
        val dia = localDate.dayOfMonth
        (mes - 1) * 30 + dia
    } catch (ex: Exception) {
        null
    }
}

fun groupByWeek(events: List<Event>): Map<Int, List<Event>> {
    if (events.isEmpty()) return emptyMap()
    val zone = ZoneId.systemDefault()
    val firstDate = events.minOf { it.startDate.toInstant().atZone(zone).toLocalDate() }

    return events.groupBy { event ->
        val eventDate = event.startDate.toInstant().atZone(zone).toLocalDate()
        val daysSinceStart = ChronoUnit.DAYS.between(
            firstDate,
            eventDate
        ).toInt()
        (daysSinceStart / 7) + 1
    }
}
