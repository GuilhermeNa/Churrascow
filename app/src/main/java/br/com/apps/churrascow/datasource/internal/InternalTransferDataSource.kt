package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.TransferDao
import br.com.apps.churrascow.model.Transfer

class InternalTransferDataSource(

    private val dao: TransferDao

) {

    suspend fun addTransfer(transfer: Transfer): Long {
        return dao.addTransfer(transfer)
    }

}