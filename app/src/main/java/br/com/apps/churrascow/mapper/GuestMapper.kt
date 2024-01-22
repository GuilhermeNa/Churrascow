package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.GuestDto

class GuestMapper {

    companion object {

        fun toDto(
            id: Long? = null,
            eventId: Long,
            name: String,
            icon: String? = null
        ): GuestDto {
            return GuestDto(
                id = id?.toString(),
                eventId = eventId.toString(),
                name = name,
                icon = icon
            )
        }
    }

}