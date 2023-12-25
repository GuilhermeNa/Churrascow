package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("email"),
        childColumns = arrayOf("idUser"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["idUser"])
    ]
)
data class Event(

    @PrimaryKey
    override val id: Long,
    val idUser: String,

    val date: LocalDateTime? = null,
    val totalValue: BigDecimal? = null

) : ExpenseGenerator(id)