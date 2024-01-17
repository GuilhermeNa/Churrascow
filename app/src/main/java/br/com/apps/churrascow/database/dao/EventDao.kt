package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.EventWithActions
import br.com.apps.churrascow.model.EventWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert
    suspend fun add(event: Event): Long

    @Transaction
    @Query("SELECT * FROM Event")
    fun getEventWithParticipants(): Flow<EventWithParticipants>

    @Transaction
    @Query("SELECT * FROM Event WHERE idUser = :userId")
    fun getEventWithActions(userId: String): Flow<List<EventWithActions>?>

    @Query("SELECT * FROM Event WHERE id = :id")
    fun getById(id: Long): Flow<Event>

    @Query("SELECT * FROM Event WHERE idUser = :userId")
    fun loadEventsByUserId(userId: String): Flow<List<Event>>
    
}