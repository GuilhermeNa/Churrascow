package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.apps.churrascow.model.Expense

@Dao
interface ExpenseDao {

    @Insert
    fun addExpense(expense: Expense): Long

}