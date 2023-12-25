package br.com.apps.churrascow.service

class ExpenseService {

/*    *//**
     * Create a new expense with the received data from view.
     *
     * This expense will integrate the event.
     *
     * @param id event's id, used as foreign key
     * @param expenseDto received data from ui layer.
     *//*
    fun newExpense(id: Long, expenseDto: ExpenseView) {
        val newExpense = Expense(
            id = id,
            generatorId = id,
            name = expenseDto.name,
            value = BigDecimal.ZERO,
            ticket = Ticket.FOOD
        )
    }*/

}