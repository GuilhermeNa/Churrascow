package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.apps.churrascow.model.Transfer

@Dao
interface TransferDao {

    @Insert
    suspend fun addTransfer(transfer: Transfer): Long

}