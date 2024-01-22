package br.com.apps.churrascow.factory

import br.com.apps.churrascow.dto.TransferDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.model.Transfer
import br.com.apps.churrascow.service.ValidationService
import br.com.apps.churrascow.util.DTO_NOT_FOUND
import br.com.apps.churrascow.util.TAG_EVENT_ID
import br.com.apps.churrascow.util.TAG_GUEST_ID
import br.com.apps.churrascow.util.TAG_VALUE
import java.math.BigDecimal

object TransferFactory {

    fun createObject(transferDto: TransferDto?): Transfer {
        val validDto = validateTransferDto(transferDto)
        return Transfer(
            eventId = validDto.eventId.toLong(),
            receiverId = validDto.receiverId.toLong(),
            senderId = validDto.senderId.toLong(),
            value = BigDecimal(validDto.value)
        )
    }

    private fun validateTransferDto(transferDto: TransferDto?): TransferDto {
        val validation = ValidationService()
        transferDto?.let { dto ->
            validation
                .forString(dto.eventId)
                .tagIdentifier(TAG_EVENT_ID)
                .cannotBeBlank()

            validation
                .forString(dto.receiverId)
                .tagIdentifier(TAG_GUEST_ID)
                .cannotBeBlank()

            validation
                .forString(dto.senderId)
                .tagIdentifier(TAG_GUEST_ID)
                .cannotBeBlank()

            validation
                .forString(dto.value)
                .tagIdentifier(TAG_VALUE)
                .cannotBeBlank()

            return dto

        } ?: throw ObjectNotFoundException(DTO_NOT_FOUND)

    }

}