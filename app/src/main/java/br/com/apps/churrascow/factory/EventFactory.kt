package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_DESCRIPTION
import br.com.apps.churrascow.util.TAG_EMAIL
import br.com.apps.churrascow.util.TAG_TITLE
import br.com.apps.churrascow.util.toLocalDateTime
import java.math.BigDecimal

object EventFactory {

    /**
     * This method create a new Event.
     * @param eventDto with valid data.
     * @return [Event]
     */
    fun createObject(eventDto: EventDto?): Event {
        val validDto = validateEventDto(eventDto)
        return Event(
            idUser = validDto.idUser,
            title = validDto.title,
            date = validDto.date?.toLocalDateTime(),
            urlImage = validDto.urlImage,
            description = validDto.description,
            _ticketGoal = BigDecimal.ZERO
        )
    }

    private fun validateEventDto(eventDto: EventDto?): EventDto {
        val validation = ValidationService()
        eventDto?.let { dto ->
            validation
                .forString(dto.idUser)
                .tagIdentifier(TAG_EMAIL)
                .cannotBeBlank()
                .mustBeEmailFormat()

            validation
                .forString(dto.title)
                .tagIdentifier(TAG_TITLE)
                .cannotBeBlank()
                .cannotBeBiggerThan(20)

            validation
                .forString(dto.description)
                .tagIdentifier(TAG_DESCRIPTION)
                .cannotBeBiggerThan(100)

            return dto

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
    }

}
