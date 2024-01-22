package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.GuestDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Guest
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_EVENT_ID
import br.com.apps.churrascow.util.TAG_NAME

object GuestFactory {

    /**
     * This method create a new Event.
     * @param guestDto with valid data.
     * @return [Guest]
     */
    fun createObject(guestDto: GuestDto?): Guest {
        val validDto = validateGuestDto(guestDto)
        return Guest(
            eventId = validDto.eventId.toLong(),
            name = validDto.name,
            icon = validDto.icon
        )
    }

    private fun validateGuestDto(guestDto: GuestDto?): GuestDto {
        val validation = ValidationService()
        guestDto?.let { dto ->
            validation
                .forString(dto.eventId)
                .tagIdentifier(TAG_EVENT_ID)
                .cannotBeBlank()

            validation
                .forString(dto.name)
                .tagIdentifier(TAG_NAME)
                .cannotBeBlank()
                .cannotBeBiggerThan(20)

            return dto

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
    }

}