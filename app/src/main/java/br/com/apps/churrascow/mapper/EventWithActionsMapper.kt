package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.dto.EventWithActionsDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.EventWithActions
import br.com.apps.churrascow.util.DTO_NOT_FOUND

class EventWithActionsMapper {

    companion object {
        fun toDto(eventWithActions: List<EventWithActions>?): List<EventWithActionsDto> {
            return eventWithActions?.map { eventWithAction ->
                EventWithActionsDto(
                    event = EventMapper.toDto(
                        idUser = eventWithAction.event.idUser,
                        id = eventWithAction.event.id,
                        title = eventWithAction.event.title,
                        description = eventWithAction.event.description,
                        date = eventWithAction.event.date,
                        guests = eventWithAction.event.guests.toString(),
                        urlImage = eventWithAction.event.urlImage
                    ),
                    actions = eventWithAction.actions.map { action ->
                        ActionDto(
                            eventId = action.eventId.toString(),
                            guestId = action.guestId.toString(),
                            value = action.value?.toPlainString(),
                            actionSummary = action.actionSummary.toString()
                        )
                    }
                )
            } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
        }
    }

}