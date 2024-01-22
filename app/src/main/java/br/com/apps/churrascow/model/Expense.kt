package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    foreignKeys = [ForeignKey(
        entity = Guest::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("guestId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["guestId"])
    ]
)
data class Expense(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val eventId: Long,
    var guestId: Long,

    val name: String,
    val value: BigDecimal,
    val ticket: Ticket

)