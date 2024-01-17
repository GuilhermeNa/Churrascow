package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalUserDataSource
import br.com.apps.churrascow.datasource.internal.InternalUserDataSource
import br.com.apps.churrascow.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest{

    private val internalData = mockk<InternalUserDataSource>()
    private val externalData = mockk<ExternalUserDataSource>()
    private val repository = UserRepository(internalData, externalData)
    private val user = User(
        email = "abc@email.com",
        name = "user",
        password = "pass123"
    )

    @Test
    fun `should call internal authenticate when trying to login`() = runTest {
        val credentials = Pair("abc@email.com", "pass123")

        coEvery {
            internalData.authenticate(credentials)
        }.returns(user)

        val result = repository.authenticate(credentials)

        assertEquals(user, result)

        coVerify {
            internalData.authenticate(credentials)
        }
    }

    @Test
    fun `should call internal add when trying to register an user`() = runTest {
        coEvery {
            internalData.newUser(user)
        }.returns(Unit)

        repository.newUser(user)

        coVerify {
            internalData.newUser(user)
        }
    }

    @Test
    fun `should call internal getById when searching an user`() = runTest {
        val userId = "a@email.com"

        coEvery {
            internalData.getById(userId)
        }.returns(flowOf(user))

        val result = repository.getById(userId).firstOrNull()

        assertEquals(user, result)

        coVerify {
            internalData.getById(userId)
        }
    }

}