package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.util.toLocalDateTime

object EventFactory {

    /**
     * This method create a new Event.
     *
     * @throws InvalidFormatException when the data required to save is not complete.
     * @throws ObjectNotFoundException when dto is not found.
     */
    fun createObject(eventDto: EventDto): Event {
        return Event(
            idUser = eventDto.idUser,
            title = eventDto.title,
            date = eventDto.date?.toLocalDateTime(),
            urlImage = eventDto.urlImage,
            description = eventDto.description
        )
    }
}
