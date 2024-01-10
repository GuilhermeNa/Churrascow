package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
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
    override val id: Long? = null,
    val idUser: String,

    val title: String,
    val date: LocalDateTime?,
    val urlImage: String? = null,
    val description: String? = null,

    ) : ExpenseGenerator(id = id) {

    /**
     * This ticket revenue goal represents the initial spending expectation for this event.
     * Is going to be divided for all valid participants.
     */
    private var ticketRevenueGoal: BigDecimal = BigDecimal.ZERO

    /**
     * This represents the amount of cash already collected in tickets.
     */
    private var collectedTicket: BigDecimal = BigDecimal.ZERO

    //---------------------------------------------------------------------------------------------//
    // CLASS METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Defines an goal for tickets revenue which is set to zero automatically.
     *
     * @param value for the total revenue goal.
     *
     * @see ticketRevenueGoal
     */
    fun createTicketRevenueGoal(value: BigDecimal?) {
        value?.let {
            ticketRevenueGoal = it
        }
    }

    /**
     * Add a payment for total collected tickets.
     *
     * @param value paid value.
     *
     * @see collectedTicket
     */
    fun addTicketPayment(value: BigDecimal?) {
        value?.let {
            if (remainingTicketAmountToReceive() > it) {
                collectedTicket.add(it)

            } else {
                throw TicketsAlreadyPaidException("")

            }
        }
    }

    /**
     * Calculates the remaining value by subtract collected tickets from revenue goal.
     *
     * @see collectedTicket
     * @see ticketRevenueGoal
     *
     * @return remaining value to pay
     */
    private fun remainingTicketAmountToReceive(): BigDecimal {
        return ticketRevenueGoal.subtract(collectedTicket)
    }


    //---------------------------------------------------------------------------------------------//
    // FOR ROOM USE
    //---------------------------------------------------------------------------------------------//

    /**
     * For Room use.
     */
    fun setTicketRevenueGoal(ticketRevenueGoal: BigDecimal) {
        this.ticketRevenueGoal = ticketRevenueGoal
    }

    /**
     * For Room use.
     */
    fun getTicketRevenueGoal(): BigDecimal {
        return this.ticketRevenueGoal
    }

    /**
     * For Room use.
     */
    fun setCollectedTicket(ticketRevenueGoal: BigDecimal) {
        this.ticketRevenueGoal = ticketRevenueGoal
    }

    /**
     * For Room use.
     */
    fun getCollectedTicket(): BigDecimal {
        return this.ticketRevenueGoal
    }

}
