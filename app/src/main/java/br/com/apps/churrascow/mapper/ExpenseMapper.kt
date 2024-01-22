package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.ExpenseDto

class ExpenseMapper {

    companion object {

        fun toDto(
            id: String?,
            eventId: String,
            guestId: String,
            name: String,
            value: String,
            ticket: String
        ): ExpenseDto {
            return ExpenseDto(
                id = id,
                eventId = eventId,
                guestId = guestId,
                name = name,
                value = value,
                ticket = ticket
            )
        }
    }

}