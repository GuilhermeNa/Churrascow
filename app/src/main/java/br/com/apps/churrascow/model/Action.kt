package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("eventId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Guest::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("guestId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Expense::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("expenseId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Action(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val eventId: Long,
    val guestId: Long? = null,
    val expenseId: Long? = null,

    val value: BigDecimal? = null,
    val actionSummary: ActionSummary,
    val description: String? = null

)