package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.ExpenseDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Expense
import br.com.apps.churrascow.model.Ticket
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_EVENT_ID
import br.com.apps.churrascow.util.TAG_GUEST_ID
import br.com.apps.churrascow.util.TAG_NAME
import br.com.apps.churrascow.util.TAG_TICKET
import br.com.apps.churrascow.util.TAG_VALUE
import br.com.apps.churrascow.util.toTicket
import java.math.BigDecimal

object ExpenseFactory {

    /**
     * This method create a new Expense.
     * @param expenseDto with valid data.
     * @return [Expense]
     */
    fun createObject(expenseDto: ExpenseDto?): Expense {
        val validDto = validateExpenseDto(expenseDto)

        return Expense(
            eventId = validDto.eventId.toLong(),
            guestId = validDto.guestId.toLong(),
            name = validDto.name,
            value = BigDecimal(validDto.value),
            ticket = validDto.ticket.toTicket()!!
        )
    }

    private fun validateExpenseDto(expenseDto: ExpenseDto?): ExpenseDto {
        val validation = ValidationService()
        expenseDto?.let { dto ->
            validation
                .forString(dto.eventId)
                .tagIdentifier(TAG_EVENT_ID)
                .cannotBeBlank()

            validation
                .forString(dto.guestId)
                .tagIdentifier(TAG_GUEST_ID)
                .cannotBeBlank()

            validation
                .forString(dto.name)
                .tagIdentifier(TAG_NAME)
                .cannotBeBlank()
                .cannotBeBiggerThan(20)

            validation
                .forString(dto.value)
                .tagIdentifier(TAG_VALUE)
                .cannotBeBlank()

            validation
                .forString(dto.ticket)
                .tagIdentifier(TAG_TICKET)
                .cannotBeBlank()
                .hasAValidEnumOnClass(Ticket::class.java)

            return dto

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)
    }


}