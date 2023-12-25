package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.mapper.RegistrationMapper
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserUseCaseTest{

    private val repository = mockk<UserRepository>()
    private val useCase = UserUseCase(repository)
    private val user = User(
        name = "Teste",
        email = "abc@email.com",
        password = "pass123"
    )
    private val registrationDto = RegistrationDto(
        name = "Teste",
        email = "abc@email.com",
        password = "123456",
        passwordConfirmation = "123456"
    )

    @Test
    fun `should call repository authenticate when trying to login`() = runTest {
        val credentials = Pair("abc@email.com", "pass123")

        coEvery {
            repository.authenticate(credentials)
        }.returns(user)

        useCase.authenticate(credentials)

        coVerify {
            repository.authenticate(credentials)
        }
    }

    @Test
    fun `should call repository add when trying to register an user`() = runTest {
        val userMapped = RegistrationMapper.toUser(registrationDto)

        coEvery {
            repository.newUser(userMapped)
        }.returns(Unit)

        useCase.newUser(registrationDto)

        coVerify {
            repository.newUser(userMapped)
        }
    }

    @Test
    fun `should call repository getById when searching an user`() = runTest {
        val userId = "a@email.com"

        coEvery {
            repository.getById(userId)
        }.returns(flowOf(user))

        useCase.getById(userId)

        coVerify {
            repository.getById(userId)
        }
    }

}