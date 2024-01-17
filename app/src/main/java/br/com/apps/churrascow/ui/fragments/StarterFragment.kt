package br.com.apps.churrascow.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import br.com.apps.churrascow.R
import br.com.apps.churrascow.database.AppDataBase
import br.com.apps.churrascow.exception.UserNotFoundException
import br.com.apps.churrascow.preferences.dataStore
import br.com.apps.churrascow.preferences.defaultValueForRememberPassword
import br.com.apps.churrascow.preferences.rememberPassword
import br.com.apps.churrascow.preferences.userLogged
import br.com.apps.churrascow.ui.activities.MainActivity
import br.com.apps.churrascow.util.TAG
import br.com.apps.churrascow.util.navigateTo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * This class is responsible for searching in preferences if the user wants to save his data
 * and id.
 */
class StarterFragment : Fragment() {

    private val userDao by lazy { AppDataBase.getDb(requireContext()).userDao() }

    //---------------------------------------------------------------------------------------------//
    // ON CREATE VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_starter, container, false)
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartingFlow()
    }

    /**
     * This method is responsible for the initial flow of the application.
     *
     * If it is the user's first time using the app, it will set [rememberPassword] of [dataStore]
     * to false.
     *
     * If [rememberPassword] in the [dataStore] is set to `true`, the app should search for the user.
     *
     * If [rememberPassword] in the [dataStore] is set to `false`, the app will restart the flow
     * again for a new login."
     */
    private fun initStartingFlow() {
        lifecycleScope.launch {
            requireContext().dataStore.data.collect { preferences ->

                val shouldRememberPassword =
                    preferences[rememberPassword] ?: firstTimeUserConnects()

                if (!shouldRememberPassword) navigateToLogin()

                try {
                    searchForUser(preferences)
                } catch (e: UserNotFoundException) {
                    e.printStackTrace()
                    navigateToLogin()
                }

            }
        }
    }

    private suspend fun firstTimeUserConnects(): Boolean {
        requireContext().dataStore.edit { preferences ->
            preferences[rememberPassword] = false
        }
        return defaultValueForRememberPassword
    }

    private suspend fun searchForUser(preferences: Preferences) {
        preferences[userLogged]?.let { userId ->
            loadUser(userId)
        } ?: throw UserNotFoundException("The user id was not found in preferences.")
    }

    private suspend fun loadUser(userId: String) {
        userDao.getById(userId).firstOrNull()?.also {
            requireContext().navigateTo(MainActivity::class.java)
            requireActivity().finish()
        } ?: throw UserNotFoundException("The user id was not found in data base.")
    }

    private suspend fun navigateToLogin() {
        requireContext().dataStore.edit { preferences ->
            preferences.remove(userLogged)
            preferences[rememberPassword] = false
        }
        Navigation.findNavController(requireView())
            .navigate(StarterFragmentDirections.actionStarterFragmentToNavLogin())
    }

}