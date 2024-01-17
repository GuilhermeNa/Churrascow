package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.math.BigDecimal

class ActionFactoryTest {

    @Test
    fun `should create an action when received data is ok`() {
        val actionDto = ActionDto(
            eventId = "1",
            guestId = "2",
            value = "10.00",
            actionSummary = "EVENT_INSERT"
        )

        val result = ActionFactory.createObject(actionDto)

        assertEquals(1L, result.eventId)
        assertEquals(2L, result.guestId)
        assertEquals(BigDecimal("10.00"), result.value)
        assertEquals(ActionSummary.EVENT_INSERT, result.actionSummary)

    }

    @Test
    fun `should handle with null when creating an action`() {
        val actionDto = ActionDto(
            eventId = "1",
            guestId = null,
            value = null,
            actionSummary = "EVENT_INSERT"
        )

        val result = ActionFactory.createObject(actionDto)

        assertEquals(1L, result.eventId)
        assertEquals(null, result.guestId)
        assertEquals(null, result.value)
        assertEquals(ActionSummary.EVENT_INSERT, result.actionSummary)
    }

    @Test
    fun `should throw ObjectNotFoundException when ActionDto is null`() {
        val resultException = assertThrows(ObjectNotFoundException::class.java) {
            ActionFactory.createObject(null)
        }

        assertEquals(DTO_NOT_FOUND, resultException.message)
    }

}