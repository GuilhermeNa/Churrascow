package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.model.Event

class EventMapper {

    companion object {
        fun toEvent(eventDto: EventDto): Event {
            return Event(
                idUser = eventDto.idUser,
                title = eventDto.title,
                description = eventDto.title,
                date = null,
            )
        }
    }

}