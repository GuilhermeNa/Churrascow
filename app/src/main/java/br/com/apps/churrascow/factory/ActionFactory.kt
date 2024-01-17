package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.SUMMARY_NOT_FOUND
import br.com.apps.churrascow.util.toActionSummary

object ActionFactory {

    fun createObject(actionDto: ActionDto?): Action {
        actionDto?.let { dto ->
            return Action(
                eventId = dto.eventId.toLong(),
                guestId = dto.guestId?.toLong(),
                value = dto.value?.toBigDecimal(),
                actionSummary = getActionSummary(dto.actionSummary)
            )
        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
    }

    private fun getActionSummary(valueInString: String): ActionSummary {
        return valueInString.toActionSummary() ?: throw ObjectNotFoundException(SUMMARY_NOT_FOUND)
    }

}