package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.EventDao
import br.com.apps.churrascow.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class InternalEventDataSourceTest {

    private val dao = mockk<EventDao>()
    private val internalData = InternalEventDataSource(dao)
    private val event = Event(
        id = null,
        idUser = "a@b.c",
        title = "event title",
        description = "event description",
        date = null
    )

    @Test
    fun `should add a new event in dao and return its id when add`() = runTest {
        coEvery {
            dao.add(event)
        }.returns(1L)

        val result = internalData.addEvent(event)

        assertEquals(1L, result)

        coVerify {
            dao.add(event)
        }
    }

    @Test
    fun `should load events from dao through id when try load events by id`() = runTest {
        val id = "a@b.c"
        val events = listOf(event)

        coEvery {
            dao.loadEventsByUserId(id)
        }.returns(flowOf(events))

        val collectedEvents = mutableListOf<Event>()
        internalData.loadEventsByUserId(id).collect { eventsList ->
            collectedEvents.addAll(eventsList)
        }

        assertEquals(events, collectedEvents)

        coVerify {
            dao.loadEventsByUserId(id)
        }
    }

}