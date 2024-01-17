package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalEventDataSource
import br.com.apps.churrascow.datasource.internal.InternalEventDataSource
import br.com.apps.churrascow.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test


class EventRepositoryTest {

    private val internalData = mockk<InternalEventDataSource>()
    private val externalData = mockk<ExternalEventDataSource>()
    private val repository = EventRepository(internalData, externalData)
    private val event = Event(
        id = null,
        idUser = "a@b.c",
        title = "event title",
        description = "event description",
        date = null
    )

    @Test
    fun `should call internal new event when trying to register an event`() = runTest {
        coEvery {
            internalData.addEvent(event)
        }.returns(1L)

        val result = repository.addEvent(event)

        Assert.assertEquals(1L, result)

        coVerify {
            internalData.addEvent(event)
        }
    }

    @Test
    fun `should call internal load events by user id when trying to load events by user id`() =
        runTest {
            val id = "a@b.c"
            val events = listOf(event)

            coEvery {
                internalData.loadEventsByUserId(id)
            }.returns(flowOf(events))

            val collectedEvents = mutableListOf<Event>()
            repository.loadEventsByUserId(id).collect { eventsList ->
                collectedEvents.addAll(eventsList)
            }

            assertEquals(events, collectedEvents)

            coVerify {
                internalData.loadEventsByUserId(id)
            }
        }

}