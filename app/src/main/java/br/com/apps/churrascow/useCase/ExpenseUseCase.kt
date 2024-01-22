package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.ExpenseDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.factory.ExpenseFactory
import br.com.apps.churrascow.mapper.ActionMapper
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Expense
import br.com.apps.churrascow.repository.ExpenseRepository

class ExpenseUseCase (

    private val repository: ExpenseRepository,
    private val actionUseCase: ActionUseCase

) {

    /**
     * Create, validate and add a new [Expense] using data from Ui tipped by user.
     * After a successful creation of an [Expense], it will add a action representing
     * the expenses creation.
     *
     * @param [expenseDto] with the data received by Ui.
     * @throws ObjectNotFoundException
     * @throws InvalidFormatException
     * @throws StringTooBigException
     */
    suspend fun registerAExpense(expenseDto: ExpenseDto?) {
        val expense = createExpense(expenseDto)
        val expenseId = addExpense(expense)
        registerAnActionForInsertion(eventId = expense.eventId, expenseId = expenseId)
    }

    private fun createExpense(validDto: ExpenseDto?): Expense {
        return ExpenseFactory.createObject(validDto)
    }

    private suspend fun addExpense(expense: Expense): Long {
        return repository.addExpense(expense)
    }

    private suspend fun registerAnActionForInsertion(eventId: Long, expenseId: Long) {
        val actionDto = ActionMapper.toDto(
            eventId = eventId,
            expenseId = expenseId,
            actionSummary = ActionSummary.EXPENSE_INSERT
        )
        actionUseCase.registerAnAction(actionDto, ActionEnum.EXPENSE)
    }

}