package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class InternalUserDataSourceTest {

    private val dao = mockk<UserDao>()
    private val internalData = InternalUserDataSource(dao)
    private val user = User(
        email = "abc@email.com",
        name = "user",
        password = "pass123"
    )

    @Test
    fun `should call dao authenticate when trying to login`() = runTest {
        val credentials = Pair("abc@email.com", "pass123")

        coEvery {
            dao.authenticate(credentials.first, credentials.second)
        }.returns(user)

        val result = internalData.authenticate(credentials)

        assertEquals(user, result)

        coVerify {
            dao.authenticate(credentials.first, credentials.second)
        }
    }

    @Test
    fun `should call dao add when trying to register an user`() = runTest {
        coEvery {
            dao.add(user)
        }.returns(Unit)

        internalData.newUser(user)

        coVerify {
            dao.add(user)
        }
    }

    @Test
    fun `should call dao getById when searching an user`() = runTest {
        val userId = "abc@email.com"

        coEvery {
            dao.getById(userId)
        }.returns(flowOf(user))

        val result = internalData.getById(userId).firstOrNull()

        assertEquals(user, result)

        coVerify {
            dao.getById(userId)
        }
    }

}