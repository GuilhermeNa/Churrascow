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
            childColumns = arrayOf("receiverId, senderId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transfer(

    @PrimaryKey
    val id: Long? = null,
    val eventId: Long,
    val receiverId: Long,
    val senderId: Long,

    val value: BigDecimal

)