package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.apps.churrascow.model.Action

@Dao
interface ActionDao {

    @Insert
    suspend fun add(action: Action)

}