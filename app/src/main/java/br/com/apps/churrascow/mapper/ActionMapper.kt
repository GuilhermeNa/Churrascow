package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.util.toStringValue

class ActionMapper {

    companion object {

        fun toDto(
            id: Long? = null,
            eventId: Long,
            guestId: Long? = null,
            expenseId: Long? = null,
            value: String? = null,
            actionSummary: ActionSummary,
            description: String? = null
        ): ActionDto {
            return ActionDto(
                id = id?.toString(),
                eventId = eventId.toString(),
                guestId = guestId?.toString(),
                expenseId = expenseId?.toString(),
                value = value,
                actionSummary = actionSummary.toStringValue(),
                description = description
            )
        }
    }

}