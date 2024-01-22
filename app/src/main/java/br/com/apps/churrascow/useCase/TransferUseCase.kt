package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.TransferDto
import br.com.apps.churrascow.factory.TransferFactory
import br.com.apps.churrascow.mapper.ActionMapper
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.model.Transfer
import br.com.apps.churrascow.repository.TransferRepository

class TransferUseCase(

    private val repository: TransferRepository,
    private val actionUseCase: ActionUseCase

) {

    suspend fun registerATransfer(transferDto: TransferDto?) {
        val transfer = createTransfer(transferDto)
        val transferId = addTransfer(transfer)
        registerAnActionForInsertion(transferId)
    }

    private suspend fun addTransfer(transfer: Transfer): Long {
        return repository.addTransfer(transfer)
    }

    private fun createTransfer(transferDto: TransferDto?): Transfer {
        return TransferFactory.createObject(transferDto)
    }

    private suspend fun registerAnActionForInsertion(transferId: Long) {
        val actionDto = ActionMapper.toDto(
            eventId = 0L,
            actionSummary = ActionSummary.TRANSFER_INSERT
        )
        actionUseCase.registerAnAction(actionDto, ActionEnum.TRANSFER)
    }

}