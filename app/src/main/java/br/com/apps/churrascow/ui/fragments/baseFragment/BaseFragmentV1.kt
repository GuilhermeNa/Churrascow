package br.com.apps.churrascow.ui.fragments.baseFragment

import android.content.Intent
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.apps.churrascow.database.AppDataBase
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.preferences.dataStore
import br.com.apps.churrascow.preferences.userLogged
import br.com.apps.churrascow.ui.activities.LoginActivity
import br.com.apps.churrascow.util.navigateTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * Base fragment v1 is used in others fragments than login or register. Use this extension on these
 * fragments would cause loops.
 */
abstract class BaseFragmentV1: BaseFragment() {

    private val userDao by lazy { AppDataBase.getDb(requireContext()).userDao() }

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val user: StateFlow<User?> = _user

    protected lateinit var userId: String

    //---------------------------------------------------------------------------------------------//
    // ON CREATE
    //---------------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            searchForLoggedUser()
        }
    }

    private suspend fun searchForLoggedUser() {
        requireContext().dataStore.data.collect { preferences ->
            preferences[userLogged]?.let { userId ->
                loadUser(userId)
            } ?: navigateToLogin()

        }
    }

    private suspend fun loadUser(userId: String) {
        userDao.getById(userId).firstOrNull()
            .also {
                _user.value = it
            }
    }

    private fun navigateToLogin() {
        requireContext().navigateTo(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        requireActivity().finish()
    }

    //---------------------------------------------------------------------------------------------//
    // PUBLIC METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Remove saved preferences of user id and return to start fragment.
     */
    protected suspend fun logout() {
        requireContext().dataStore.edit { preferences ->
            preferences.remove(userLogged)
        }
    }



}