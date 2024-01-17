package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.factory.ActionFactory
import br.com.apps.churrascow.mapper.ActionMapper
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.Guest
import br.com.apps.churrascow.repository.ActionRepository
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.TAG_ACTION_SUMMARY
import br.com.apps.churrascow.util.TAG_EVENT_ID

class ActionUseCase<T>(

    private val repository: ActionRepository

) {

    suspend fun addAction(action: Action) {
        repository.addAction(action)
    }

    suspend fun registerAnAction(
        t: T,
        eventId: Long,
        guestId: Long? = null,
        value: String? = null,
        actionSummary: ActionSummary
    ) {
        when (t) {
            is Event -> {
                val actionDto = createActionDto(eventId, null, null, actionSummary)
                validateActionDto(actionDto)
                val action = ActionFactory.createObject(actionDto)
                addAction(action)
            }

            is Guest -> {
                val actionDto = createActionDto(eventId, guestId, value, actionSummary)

            }
        }
    }

    private fun createActionDto(
        eventId: Long,
        guestId: Long?,
        value: String?,
        actionSummary: ActionSummary
    ): ActionDto {
        return ActionMapper.toDto(
            eventId = eventId,
            guestId = guestId,
            value = value,
            actionSummary = actionSummary
        )
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
        }


    }


}