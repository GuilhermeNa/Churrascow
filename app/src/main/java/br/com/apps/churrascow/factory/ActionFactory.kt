package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.exception.InvalidEnumReferenceException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.ACTION_NOT_FOUND
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.NO_TAG
import br.com.apps.churrascow.util.SUMMARY_NOT_FOUND
import br.com.apps.churrascow.util.TAG_ACTION_SUMMARY
import br.com.apps.churrascow.util.TAG_EVENT_ID
import br.com.apps.churrascow.util.toActionSummary

object ActionFactory {

    /**
     * This method create a new Action.
     * @param actionDto has only validated non-null parameters.
     * @param actionEnum which class should this action be created.
     */
    fun createObject(actionDto: ActionDto?, actionEnum: ActionEnum): Action {
        val validDto = validateActionDto(actionDto)

        return when (actionEnum) {
            ActionEnum.EVENT -> actionForEvent(validDto)
            ActionEnum.GUEST -> actionForGuest(validDto)
            ActionEnum.EXPENSE -> actionForExpense(validDto)

            else -> { throw InvalidEnumReferenceException(ACTION_NOT_FOUND)}
        }

    }

    private fun validateActionDto(actionDto: ActionDto?): ActionDto {
        val validation = ValidationService()
        actionDto?.let { dto ->

            validation
                .forString(dto.eventId)
                .tagIdentifier(TAG_EVENT_ID)
                .cannotBeBlank()

            validation
                .forString(dto.actionSummary)
                .tagIdentifier(TAG_ACTION_SUMMARY)
                .cannotBeBlank()
                .hasAValidEnumOnClass(ActionSummary::class.java)

            return dto

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
    }

    private fun actionForEvent(actionDto: ActionDto): Action {
        return Action(
            eventId = actionDto.eventId.toLong(),
            value = actionDto.value?.toBigDecimal(),
            actionSummary = actionDto.actionSummary.toActionSummary()!!,
            description = getDescription(actionDto)
        )
    }

    private fun actionForGuest(actionDto: ActionDto): Action {
        return Action(
            eventId = actionDto.eventId.toLong(),
            guestId = actionDto.guestId?.toLong(),
            value = actionDto.value?.toBigDecimal(),
            actionSummary = actionDto.actionSummary.toActionSummary()!!,
            description = getDescription(actionDto)
        )
    }

    private fun actionForExpense(actionDto: ActionDto): Action {
        return Action(
            eventId = actionDto.eventId.toLong(),
            expenseId = actionDto.expenseId?.toLong(),
            value = actionDto.value?.toBigDecimal(),
            actionSummary = actionDto.actionSummary.toActionSummary()!!,
            description = getDescription(actionDto)
        )
    }

    private fun getDescription(actionDto: ActionDto): String {
        return when (actionDto.actionSummary.toActionSummary()) {

            ActionSummary.EVENT_INSERT -> "Evento criado."

            ActionSummary.EVENT_REVENUE_SET -> "Valor definido."

            ActionSummary.GUEST_INSERT -> "Convidado inserido."

            ActionSummary.GUEST_REMOVED -> "Convidado removido."

            ActionSummary.GUEST_PAID -> "Convidado pagou."

            ActionSummary.EXPENSE_INSERT -> "Despesa inserida."

            ActionSummary.EXPENSE_REMOVED -> "Despesa removida."

            else -> throw InvalidEnumReferenceException(SUMMARY_NOT_FOUND)
        }
    }

}