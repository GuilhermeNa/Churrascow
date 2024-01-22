package br.com.apps.churrascow.util

import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Ticket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(): LocalDateTime {
    val formatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return LocalDateTime.parse(this, formatter)
}

fun String.toActionSummary(): ActionSummary? {
    return ActionSummary.values().find {
        it.toString() == this
    }
}

fun String.toTicket(): Ticket? {
    return Ticket.values().find {
        it.toString() == this
    }
}