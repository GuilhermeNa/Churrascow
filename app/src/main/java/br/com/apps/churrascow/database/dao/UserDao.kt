package br.com.apps.churrascow.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.apps.churrascow.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun add(user: User)

    @Query("SELECT * FROM User " +
            "WHERE email = :userId " +
            "AND password = :password")
    suspend fun authenticate(userId: String, password: String): User?

    @Query("SELECT * FROM User WHERE email = :userId")
    fun getById(userId: String): Flow<User>

}