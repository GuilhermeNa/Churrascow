package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.EventDao
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.EventWithActions
import kotlinx.coroutines.flow.Flow

class InternalEventDataSource(

    private val dao: EventDao

) {

    /**
     * Adding a new event.
     *
     * @param event New event.
     */
    suspend fun addEvent(event: Event): Long {
        return dao.add(event)
    }

    /**
     * Loads all event's linked to an userId.
     *
     * @param userId user email.
     *
     * @return a flow with a list of all user's event.
     */
    fun loadEventsByUserId(userId: String): Flow<List<Event>> {
        return dao.loadEventsByUserId(userId)
    }

    fun loadEventsWithActions(userId: String): Flow<List<EventWithActions>?> {
        return dao.getEventWithActions(userId)
    }

}