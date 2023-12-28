package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalEventDataSource
import br.com.apps.churrascow.datasource.internal.InternalEventDataSource
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
            internalData.newEvent(event)
        }.returns(Unit)

        repository.newEvent(event)

        coVerify {
            internalData.newEvent(event)
        }
    }

    @Test
    fun `should call internal load events by user id when trying to load events by user id`() = runTest {
        val id = "a@b.c"
        val events = listOf(event, event)

        coEvery {
            internalData.loadEventsByUserId(id)
        }.returns(flowOf(events))

        repository.loadEventsByUserId(id)

        coVerify {
            internalData.loadEventsByUserId(id)
        }
    }

}