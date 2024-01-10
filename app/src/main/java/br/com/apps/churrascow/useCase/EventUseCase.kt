package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.factory.EventFactory
import br.com.apps.churrascow.mapper.EventMapper
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.repository.EventRepository
import br.com.apps.churrascow.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Base class of business logic. An event is the origin to the entire application flow and here you
 * can access its basic behaviors.
 * This class manage and redirect its sons behaviors.
 *
 */
class EventUseCase(

    private val repository: EventRepository

) {

    /**
     * Map a Data transfer object to an Event.
     *
     * @param eventDto received data from view.
     *
     * @return Event - mapped object.
     */
    fun mapToEvent(eventDto: EventDto): Event {
        return EventMapper.toEvent(eventDto)
    }

    /**
     * Adding a new event.
     *
     * @param event New event.
     */
    suspend fun addEvent(event: Event) {
        repository.addEvent(event)
    }

    /**
     * Loads all event's linked to an userId.
     *
     * @param userId user email.
     *
     * @return a flow with a list of all user's event.
     */
    fun loadEventsByUserId(userId: String): Flow<List<Event>> {
        return repository.loadEventsByUserId(userId)
    }

    fun createEvent(eventDto: EventDto): Event {
        return EventFactory.createObject(eventDto)
    }

}