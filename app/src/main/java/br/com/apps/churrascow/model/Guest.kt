package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

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
    val id: Long? = null,
    var eventId: Long,

    val name: String,
    val icon: String? = null,

    private var _received: BigDecimal = BigDecimal.ZERO,
    private var _expend: BigDecimal = BigDecimal.ZERO

) {

    val received get() = _received
    val expend get() = _expend

    fun addPaidValue(value: BigDecimal?) {
        value?.let{
            _received.add(value)
        }
    }

    fun addReceivedValue(value: BigDecimal?) {
        value?.let {
            _expend.add(value)
        }
    }

    fun balance(): BigDecimal {
        return _received.subtract(_expend)
    }

}