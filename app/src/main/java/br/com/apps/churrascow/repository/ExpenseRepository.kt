package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalExpenseDataSource
import br.com.apps.churrascow.datasource.internal.InternalExpenseDataSource
import br.com.apps.churrascow.model.Expense

class ExpenseRepository(

    private val internalDataSource: InternalExpenseDataSource,
    private val externalDataSource: ExternalExpenseDataSource

) {

    suspend fun addExpense(expense: Expense): Long {
        return internalDataSource.addExpense(expense)
    }


}