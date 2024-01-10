package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.mapper.EventMapper
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.repository.EventRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EventUseCaseTest{

    private val repository = mockk<EventRepository>()
    private val useCase = EventUseCase(repository)
    private val event = Event(
        id = null,
        idUser = "a@b.c",
        title = "event title",
        description = "event description",
        date = null
    )
    private val eventDto = EventDto(
        idUser = "a@b.c",
        title = "event title",
        description = "event description",
        date = null,
    )

    @Test
    fun `should call event mapper when trying to map`() {
        mockkObject(EventMapper)

        every {
            EventMapper.toEvent(eventDto)
        }.returns(event)

        useCase.mapToEvent(eventDto)

        verify {
            EventMapper.toEvent(eventDto)
        }
    }

    @Test
    fun `should call repository newUser when trying to add new event`() = runTest {
        coEvery {
            repository.addEvent(event)
        }.returns(Unit)

        useCase.addEvent(event)

        coVerify {
            repository.addEvent(event)
        }
    }

    @Test
    fun `should call repository load events by user id when trying to load events by user`() = runTest {
        val id = "a@b.c"
        val events = listOf(event, event)

        coEvery {
            repository.loadEventsByUserId(id)
        }.returns(flowOf(events))

        useCase.loadEventsByUserId(id)

        coVerify {
            repository.loadEventsByUserId(id)
        }
    }





}