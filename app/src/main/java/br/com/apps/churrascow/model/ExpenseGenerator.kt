package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.lang.annotation.Inherited

@Entity
abstract class ExpenseGenerator(

    @PrimaryKey(autoGenerate = true)
    open val id: Long

)