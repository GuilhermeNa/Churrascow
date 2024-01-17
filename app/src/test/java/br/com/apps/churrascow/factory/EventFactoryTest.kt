package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.BlankStringException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.util.toLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EventFactoryTest {

    @Test
    fun `should create an event when received data is ok`() {
        val eventDto = EventDto(
            idUser = "a@b.c",
            title = "Test Event",
            date = "2024-01-16T12:00:00",
            urlImage = "https://example.com/image.jpg",
            description = "Test description"
        )

        val resultEvent = EventFactory.createObject(eventDto)

        assertEquals("a@b.c", resultEvent.idUser)
        assertEquals("Test Event", resultEvent.title)
        assertEquals("2024-01-16T12:00:00".toLocalDateTime(), resultEvent.date)
        assertEquals("https://example.com/image.jpg", resultEvent.urlImage)
        assertEquals("Test description", resultEvent.description)

    }

    @Test
    fun `should handle with null when creating an event`(){
        val eventDto = EventDto(
            idUser = "a@b.c",
            title = "Test Event",
            date = null,
            urlImage = null,
            description = null
        )

        val resultEvent = EventFactory.createObject(eventDto)

        assertEquals("a@b.c", resultEvent.idUser)
        assertEquals("Test Event", resultEvent.title)
        assertEquals(null, resultEvent.date)
        assertEquals(null, resultEvent.urlImage)
        assertEquals(null, resultEvent.description)
    }

    @Test
    fun `should throw exception when title is blank`() {
        val eventDto = EventDto(
            idUser = "a@b.c",
            title = "",
        )
        assertThrows(BlankStringException::class.java) {
            EventFactory.createObject(eventDto)
        }
    }

}