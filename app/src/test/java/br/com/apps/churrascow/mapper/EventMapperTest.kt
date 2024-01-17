package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.exception.BlankStringException
import br.com.apps.churrascow.util.formatToString
import br.com.apps.churrascow.service.STRING_IS_BLANK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.LocalDateTime

class EventMapperTest {

    @Test
    fun `should convert to dto when data is ok`() {
        val idUser = "a@b.c"
        val id = 1L
        val title = "Test title"
        val description = "Test description"
        val date = LocalDateTime.now()
        val guests = "0"
        val urlImage = "https://example.com/image.jpg"

        val result =
            EventMapper.toDto(idUser, id, title, description, date, guests, urlImage)

        assertEquals("a@b.c", result.idUser)
        assertEquals("1", result.id)
        assertEquals("Test title", result.title)
        assertEquals("Test description", result.description)
        assertEquals(date.formatToString(), result.date)
        assertEquals("0", result.guests)
        assertEquals("https://example.com/image.jpg", result.urlImage)

    }

    @Test
    fun `should handle with null when creating a dto`() {
        val idUser = "a@b.c"
        val id = null
        val title = "Test title"
        val description = null
        val date = null
        val guests = null
        val urlImage = null

        val result =
            EventMapper.toDto(idUser, id, title, description, date, guests, urlImage)

        assertEquals("a@b.c", result.idUser)
        assertEquals(null, result.id)
        assertEquals("Test title", result.title)
        assertEquals(null, result.description)
        assertEquals(null, result.date)
        assertEquals(null, result.guests)
        assertEquals(null, result.urlImage)

    }

    @Test
    fun `should throw BlankStringException when title is blank`() {
        val idUser = "a@b.c"
        val title = ""

        val resultException =
            assertThrows(BlankStringException::class.java) {
                EventMapper.toDto(
                    idUser = idUser,
                    title = title
                )
            }

        assertEquals(STRING_IS_BLANK, resultException.message)

    }

}