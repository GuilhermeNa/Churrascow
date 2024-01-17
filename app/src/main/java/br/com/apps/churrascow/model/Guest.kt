package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Event::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("eventId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["eventId"])
    ]
)
data class Guest(

    @PrimaryKey
    override val id: Long,
    var eventId: Long,

    val name: String,
    val icon: Long?,

    ) : ExpenseGenerator(
    id = id
) {

}