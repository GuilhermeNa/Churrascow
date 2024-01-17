package br.com.apps.churrascow.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun LocalDateTime.formatToStringBr(): String {
    val dayOfWeek = this.dayOfMonth
    val monthOfYear = this.month
    val year = this.year

    val locale = Locale("pt", "BR")
    val monthInPtBr = monthOfYear.getDisplayName(TextStyle.FULL, locale)
    val month =
        monthInPtBr.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

    return "$dayOfWeek, de $month de $year"
}

fun LocalDateTime.formatToString(): String {
    val formatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return this.format(formatter)
}

