package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.model.Event
import java.time.LocalDateTime

class EventMapper {

    companion object {

        fun toEvent(eventDto: EventDto): Event {
            return Event(
                idUser = "teste",
                title = "teste",
                description = eventDto.title,
                date = LocalDateTime.now(),
            )
        }

        fun toDto(
            idUser: String,
            title: String,
            date: LocalDateTime?,
            urlImage: String?
        ): EventDto {
            val dateString = date?.toString() ?: LocalDateTime.now().toString()
            val urlString = urlImage ?: ""
            return EventDto(
                idUser = idUser,
                title = title,
                date = dateString,
                urlImage = urlString,
            )
        }

    }
}