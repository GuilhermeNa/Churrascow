package br.com.apps.churrascow.factory

import android.util.Log
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.util.TAG_ERROR
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val UNABLE_TO_CREATE_DTO_NOT_FOUND = "Nao foi possivel registrar, dto não encontrado"

private const val UNABLE_TO_CREATE_TITLE_IS_BLANK = "Nao foi possivel registrar, titulo vazio"

object EventFactory {

    /**
     * This method create a new Event.
     *
     * @throws InvalidFormatException when the data required to save is not complete.
     * @throws ObjectNotFoundException when dto is not found.
     */
    fun createObject(eventDto: EventDto?): Event {
        eventDto?.let {
            return Event(
                idUser = it.idUser,
                title = getTitle(it),
                date = getDate(it),
                urlImage = it.urlImage
            )
        } ?: throw ObjectNotFoundException(UNABLE_TO_CREATE_DTO_NOT_FOUND)
    }

    private fun getTitle(eventDto: EventDto): String {
        if( eventDto.title.isNotBlank()) {
            return eventDto.title
        } else {
            throw InvalidFormatException(UNABLE_TO_CREATE_TITLE_IS_BLANK)
        }
    }

    private fun getDate(eventDto: EventDto): LocalDateTime {
        var date = eventDto.date

        if(date.isBlank()){ date = LocalDateTime.now().toString() }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        return LocalDateTime.parse(date, formatter)
    }

}