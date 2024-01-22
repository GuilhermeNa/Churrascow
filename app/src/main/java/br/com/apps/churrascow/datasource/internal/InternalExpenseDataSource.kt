package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.ExpenseDao
import br.com.apps.churrascow.model.Expense

class InternalExpenseDataSource(

    private val dao: ExpenseDao

) {

    fun addExpense(expense: Expense): Long {
        return dao.addExpense(expense)
    }

}