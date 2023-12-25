package br.com.apps.churrascow.service

class EventService {
    private val participantService = ParticipantService()
    private val expenseService = ExpenseService()

/*    *//**
     * Create a new event.
     *
     * @param eventDto received data from ui layer.
     *//*
    fun newEvent(eventDto: EventView) {
        val newEvent = Event(
            id = 1L,
            idUser = "teste",
            date = LocalDateTime.now(),
            totalValue = null
        )
    }*/

/*    *//**
     * Create an event's participant and link them by event's id as foreign key.
     *
     * @param id event's id, used as foreign key in Participant creation.
     * @param participantDto received data from ui layer.
     *//*
    fun newEventsParticipant(id: Long, participantDto: ParticipantView) {
        participantService.newParticipant(id, participantDto)
    }

    *//**
     *Create an event's expense and link them by event's id as foreign key.
     *
     * @param id event's id, used as foreign key in Expense creation.
     * @param expenseDto received data from ui layer.
     *//*
    fun newEventsExpense(id: Long, expenseDto: ExpenseView) {
        expenseService.newExpense(id, expenseDto)
    }*/


}