package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.service.ExpenseService

class ExpenseUseCase {

    private val service = ExpenseService()

/*    *//**
     * Create a new expense with the received data from view.
     *
     * This expense will integrate the event.
     *
     * @param id event's id, used as foreign key
     * @param expenseDto received data from ui layer.
     *//*
    fun newExpense(id: Long, expenseDto: ExpenseView) {
        service.newExpense(id, expenseDto)
    }*/

}