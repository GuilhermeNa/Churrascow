package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.GuestDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.factory.GuestFactory
import br.com.apps.churrascow.mapper.ActionMapper
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Guest
import br.com.apps.churrascow.repository.GuestRepository
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_EVENT_ID
import br.com.apps.churrascow.util.TAG_NAME

class GuestUseCase (

    private val repository: GuestRepository,
    private val actionUseCase: ActionUseCase

) {

    /**
     * Create, validate and add a new [Guest] using data from Ui tipped by user.
     * After a successful creation of an [Guest], it will add a [Action] representing
     * the events creation.
     *
     * @param [guestDto] with the data received by Ui.
     * @throws ObjectNotFoundException
     * @throws InvalidFormatException
     * @throws StringTooBigException
     */
    suspend fun registerAGuest(guestDto: GuestDto?) {
        val guest = createGuest(guestDto)
        val guestId = addGuest(guest)
        registerAnActionForInsertion(eventId = guest.eventId, guestId = guestId)
    }

    private fun createGuest(guestDto: GuestDto?): Guest {
        return GuestFactory.createObject(guestDto)
    }

    private suspend fun addGuest(guest: Guest): Long {
        return repository.addGuest(guest)
    }

    private suspend fun registerAnActionForInsertion(eventId: Long, guestId: Long) {
        val actionDto = ActionMapper.toDto(
            eventId = eventId,
            guestId = guestId,
            actionSummary = ActionSummary.GUEST_INSERT
        )
        actionUseCase.registerAnAction(actionDto, ActionEnum.GUEST)
    }

}