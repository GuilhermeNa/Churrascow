package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.mapper.RegistrationMapper
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(

    private val repository: UserRepository

) {

    /**
     * Authenticating an user.
     *
     * @param credentials is a pair which contain login and password.
     *
     *@return User?
     */
    suspend fun authenticate(credentials: Pair<String, String>): User? {
        return repository.authenticate(credentials)
    }

    /**
     * Adding a new user.
     *
     * @param user New user.
     */
    suspend fun newUser(registrationDto: RegistrationDto) {
        val user = RegistrationMapper.toUser(registrationDto)
        repository.newUser(user)
    }

    /**
     * Searching for an already existent User.
     *
     * @param id Which is used for comparison.
     *
     * @return Flow<User>
     */
    fun getById(id: String): Flow<User> {
        return repository.getById(id)
    }

}