package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.Guest

@Dao
interface GuestDao {

    @Insert
    suspend fun addGuest(guest: Guest): Long

}