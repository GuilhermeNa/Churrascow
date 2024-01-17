package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.util.formatToString
import java.time.LocalDateTime

class EventMapper {

    companion object {

        fun toDto(
            idUser: String,
            id: Long? = null,
            title: String,
            description: String? = null,
            date: LocalDateTime? = null,
            guests: String? = null,
            urlImage: String? = null
        ): EventDto {
            return EventDto(
                idUser = idUser,
                id = id?.toString(),
                title = title,
                description = description,
                date = date?.formatToString(),
                guests = guests,
                urlImage = urlImage,
            )
        }

    }
}