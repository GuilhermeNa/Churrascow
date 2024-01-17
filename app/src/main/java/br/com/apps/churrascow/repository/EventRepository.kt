package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalEventDataSource
import br.com.apps.churrascow.datasource.internal.InternalEventDataSource
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.EventWithActions
import kotlinx.coroutines.flow.Flow

class EventRepository(

    private val internalDataSource: InternalEventDataSource,
    private val externalDataSource: ExternalEventDataSource

) {

    /**
     * Adding a new event.
     *
     * @param event New event.
     */
    suspend fun addEvent(event: Event): Long {
        return internalDataSource.addEvent(event)
    }

    /**
     * Loads all event's linked to an userId.
     *
     * @param userId user email.
     *
     * @return a flow with a list of all user's event.
     */
    fun loadEventsByUserId(userId: String): Flow<List<Event>> {
        return internalDataSource.loadEventsByUserId(userId)
    }

    fun loadEventsWithActions(userId: String): Flow<List<EventWithActions>?> {
        return internalDataSource.loadEventsWithActions(userId)
    }

}