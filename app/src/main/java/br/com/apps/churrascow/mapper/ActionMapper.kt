package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.util.toActionSummary
import br.com.apps.churrascow.util.toStringValue
import java.math.BigDecimal

class ActionMapper {

    companion object {

        fun toDto(
            id: Long? = null,
            eventId: Long,
            guestId: Long? = null,
            value: String? = null,
            actionSummary: ActionSummary
        ): ActionDto {
            return ActionDto(
                id = id?.toString(),
                eventId = eventId.toString(),
                guestId = guestId?.toString(),
                value = value,
                actionSummary = actionSummary.toStringValue()
            )
        }
    }

}