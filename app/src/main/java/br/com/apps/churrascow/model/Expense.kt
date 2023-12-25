package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    foreignKeys = [ForeignKey(
        entity = ExpenseGenerator::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("generatorId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["generatorId"])
    ]
)
data class Expense(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var generatorId: Long,

    val name: String,
    val value: BigDecimal,
    val ticket: Ticket

)