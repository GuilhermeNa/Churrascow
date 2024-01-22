package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalTransferDataSource
import br.com.apps.churrascow.datasource.internal.InternalTransferDataSource
import br.com.apps.churrascow.model.Transfer

class TransferRepository(

    private val internalDataSource: InternalTransferDataSource,
    private val externalDataSource: ExternalTransferDataSource

) {

    suspend fun addTransfer(transfer: Transfer): Long {
        return internalDataSource.addTransfer(transfer)
    }

}