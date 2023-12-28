package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.model.User
import kotlinx.coroutines.flow.Flow

class InternalUserDataSource(

    private val userDao: UserDao

) {

    /**
     * Authenticating an user.
     *
     * @param credentials is a pair which contain login and password.
     *
     *@return User?
     */
    suspend fun authenticate(credentials: Pair<String, String>): User? {
        return userDao.authenticate(
            credentials.first,
            credentials.second
        )
    }

    /**
     * Adding a new user.
     *
     * @param user New user.
     */
    suspend fun newUser(user: User) {
         userDao.add(user)
    }

    /**
     * Searching for an already existent User.
     *
     * @param id Which is used for comparison.
     *
     * @return Flow<User>
     */
    fun getById(id: String): Flow<User> {
        return userDao.getById(id)
    }


}