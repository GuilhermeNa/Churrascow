package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.service.EventService

/**
 * Base class of business logic. An event is the origin to the entire application flow and here you
 * can access its basic behaviors.
 * This class manage and redirect its sons behaviors.
 *
 */
class EventUseCase {

    private val service = EventService()
    private val participantUseCase = ParticipantUseCase()
    private val expenseUserUseCase = ExpenseUseCase()

/*    *//**
     * Create a new event with the received data from view.
     *
     * @param eventDto received data from ui layer.
     *//*
    fun newEvent(eventDto: EventView){
        service.newEvent(eventDto)
    }*/

/*    *//**
     * Create a new event's participant with the received data from view.
     *
     * This participant will integrate the event.
     *
     * @param id event's id, used as foreign key
     * @param participantDto received data from ui layer.
     *//*
    fun newEventsParticipant(id: Long, participantDto: ParticipantView){
        participantUseCase.newParticipant(id, participantDto)
    }*/

/*    *//**
     * Create a new event's expense with the received data from view.
     *
     * This expense will integrate the event.
     *
     * @param id event's id, used as foreign key.
     * @param expenseDto received data from ui layer.
     *//*
    fun newEventsExpense(id: Long, expenseDto: ExpenseView){
        expenseUserUseCase.newExpense(id, expenseDto)
    }*/

     /**
     * This method is going to get all the expenses and participants, calculate each one payment
     * balance and return the value.
     */
    fun divideValue(){

    }


}