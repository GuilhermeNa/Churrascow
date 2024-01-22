package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.factory.EventFactory
import br.com.apps.churrascow.mapper.ActionMapper
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.EventWithActions
import br.com.apps.churrascow.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class EventUseCase(

    private val repository: EventRepository,
    private val actionUseCase: ActionUseCase

) {

    /**
     * Create, validate and add a new event using data from Ui tipped by user.
     * After a successful creation of an [Event], it will add a [Action] representing
     * the events creation.
     *
     * @param [eventDto] with the data received by Ui.
     * @throws ObjectNotFoundException
     * @throws InvalidFormatException
     * @throws StringTooBigException
     */
    suspend fun registerAnEvent(eventDto: EventDto?) {
        val event = createEvent(eventDto)
        val eventId = addEvent(event)
        registerAnActionForInsertion(eventId)
    }

    private fun createEvent(eventDto: EventDto?): Event {
        return EventFactory.createObject(eventDto)
    }

    private suspend fun addEvent(event: Event): Long {
        return repository.addEvent(event)
    }

    private suspend fun registerAnActionForInsertion(eventId: Long) {
        val actionDto = ActionMapper.toDto(
            eventId = eventId,
            actionSummary = ActionSummary.EVENT_INSERT
        )
        actionUseCase.registerAnAction(actionDto, ActionEnum.EVENT)
    }

    /**
     * Loads all the events of an user and its respectively actions.
     *
     * @param userId user email.
     *
     * @return a flow with a list of all user's event.
     */
    fun loadEventsWithActions(userId: String): Flow<List<EventWithActions>?> {
        return repository.loadEventsWithActions(userId)
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

    fun createTicketGoal( event: Event, value: BigDecimal) {
        event.createTicketGoal(value)
        //todo precisamos salvar o evento
    }


}