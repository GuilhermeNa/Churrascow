package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.EventDao
import br.com.apps.churrascow.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
    fun `should call dao new event when trying to register an event`() = runTest{
        coEvery {
            dao.add(event)
        }.returns(Unit)

        internalData.newEvent(event)

        coVerify {
            dao.add(event)
        }
    }

    @Test
    fun `should call dao load events by user id when trying to load events by user id`() = runTest {
        val id = "a@b.c"
        val events = listOf(event, event)

        coEvery {
            dao.loadEventsByUserId(id)
        }.returns(flowOf(events))

        internalData.loadEventsByUserId(id)

        coVerify {
            dao.loadEventsByUserId(id)
        }
    }

}