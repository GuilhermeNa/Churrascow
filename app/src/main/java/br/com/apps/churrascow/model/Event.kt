package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.apps.churrascow.exception.NoGuestsException
import br.com.apps.churrascow.exception.TicketsAlreadyPaidException
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
    val id: Long? = null,
    val idUser: String,

    val title: String,
    val date: LocalDateTime? = LocalDateTime.now(),
    val urlImage: String? = null,
    val description: String? = null,

    /**
     * This ticket revenue goal represents the initial spending expectation for this event.
     * Is going to be divided for all valid guests.
     */
    private var _ticketGoal: BigDecimal,

) {

    val ticketGoal get() = _ticketGoal

    /**
     * Defines an goal for tickets revenue which is set to zero automatically.
     *
     * @param value for the total revenue goal.
     *
     * @see ticketGoal
     */
    fun createTicketGoal(value: BigDecimal?) {
        value?.let {
            _ticketGoal = it
        }
    }


    //---------------------------------------------------------------------------------------------//
    // FOR ROOM USE
    //---------------------------------------------------------------------------------------------//

/*    *//**
     * For Room use.
     *//*
    fun setTicketRevenueGoal(ticketRevenueGoal: BigDecimal) {
        this.ticketRevenueGoal = ticketRevenueGoal
    }

    *//**
     * For Room use.
     *//*
    fun getTicketRevenueGoal(): BigDecimal {
        return this.ticketRevenueGoal
    }

    *//**
     * For Room use.
     *//*
    fun setCollectedTicket(ticketRevenueGoal: BigDecimal) {
        this.ticketRevenueGoal = ticketRevenueGoal
    }

    *//**
     * For Room use.
     *//*
    fun getCollectedTicket(): BigDecimal {
        return this.ticketRevenueGoal
    }*/


}
