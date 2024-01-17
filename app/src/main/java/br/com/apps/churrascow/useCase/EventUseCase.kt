package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.factory.EventFactory
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.EventWithActions
import br.com.apps.churrascow.repository.EventRepository
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_DESCRIPTION
import br.com.apps.churrascow.util.TAG_TITLE
import kotlinx.coroutines.flow.Flow

/**
 * Base class of business logic. An event is the origin to the entire application flow and here you
 * can access its basic behaviors.
 * This class manage and redirect its sons behaviors.
 *
 */
class EventUseCase(

    private val repository: EventRepository,
    private val actionUseCase: ActionUseCase<Event>

) {

    /**
     * Adding a new [Event] and register an [Action] for it.
     *
     * @param event New event.
     */
    suspend fun addEvent(event: Event) {
        val eventId = repository.addEvent(event)
        actionUseCase.registerAnAction(
            t = event,
            eventId = eventId,
            actionSummary = ActionSummary.EVENT_INSERT
        )

        //TODO -> adicionar a ação do evento
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

    /**
     * Create a new event using data from Ui tipped by user.
     * @param [eventDto] with the data received by Ui.
     * @return created Event
     * @throws ObjectNotFoundException
     * @throws InvalidFormatException
     * @throws StringTooBigException
     */
    fun createEvent(eventDto: EventDto?): Event {
        val validDto = validateEventDto(eventDto)
        return EventFactory.createObject(validDto)
    }

    private fun validateEventDto(eventDto: EventDto?): EventDto {
        val validation = ValidationService()

        eventDto?.let { dto ->
            validation
                .forString(dto.idUser)
                .cannotBeBlank()
                .mustBeEmailFormat()

            validation
                .forString(dto.title)
                .tagIdentifier(TAG_TITLE)
                .cannotBeBlank()
                .cannotBeBiggerThan(20)

            validation
                .forString(dto.description)
                .tagIdentifier(TAG_DESCRIPTION)
                .cannotBeBiggerThan(100)

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)

        return eventDto
    }

    fun loadEventsWithActions(userId: String): Flow<List<EventWithActions>?> {
        return repository.loadEventsWithActions(userId)
    }

}