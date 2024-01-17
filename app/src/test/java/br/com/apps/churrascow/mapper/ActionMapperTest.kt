package br.com.apps.churrascow.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class ActionMapperTest {

    @Test
    fun `should convert to dto when data is ok`() {
        val id = 1L
        val eventId = 1L
        val guestId = 1L
        val value = "10.00"
        val actionSummary = "EVENT_INSERT"

        val result =
        ActionMapper.toDto(id, eventId, guestId, value, actionSummary)

        assertEquals("1", result.id)
        assertEquals("1", result.eventId)
        assertEquals("1", result.guestId)
        assertEquals("10.00", result.value)
        assertEquals("EVENT_INSERT", result.actionSummary)

    }

    @Test
    fun `should handle with null when creating a dto`() {
        val id = null
        val eventId = 1L
        val guestId = null
        val value = null
        val actionSummary = "EVENT_INSERT"

        val result =
            ActionMapper.toDto(id, eventId, guestId, value, actionSummary)

        assertEquals(null, result.id)
        assertEquals("1", result.eventId)
        assertEquals(null, result.guestId)
        assertEquals(null, result.value)
        assertEquals("EVENT_INSERT", result.actionSummary)

    }



}