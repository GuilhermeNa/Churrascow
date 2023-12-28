package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalUserDataSource
import br.com.apps.churrascow.datasource.internal.InternalUserDataSource
import br.com.apps.churrascow.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(

    private val internalDataSource: InternalUserDataSource,
    private val externalDataSource: ExternalUserDataSource

) {

    /**
     * Authenticating an user.
     *
     * @param credentials is a pair which contain login and password.
     *
     *@return User?
     */
    suspend fun authenticate(credentials: Pair<String, String>): User? {
        return internalDataSource.authenticate(credentials)
    }

    /**
     * Adding a new user.
     *
     * @param user New user.
     */
    suspend fun newUser(user: User) {
        internalDataSource.newUser(user)
    }

    /**
     * Searching for an already existent User.
     *
     * @param id Which is used for comparison.
     *
     * @return Flow<User>
     */
    fun getById(id: String): Flow<User> {
        return internalDataSource.getById(id)
    }

}