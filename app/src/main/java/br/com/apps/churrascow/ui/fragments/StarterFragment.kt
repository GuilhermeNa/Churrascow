package br.com.apps.churrascow.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import br.com.apps.churrascow.R
import br.com.apps.churrascow.preferences.dataStore
import br.com.apps.churrascow.preferences.defaultValueForRememberPassword
import br.com.apps.churrascow.preferences.rememberPassword
import br.com.apps.churrascow.preferences.userLogged
import br.com.apps.churrascow.ui.activities.MainActivity
import br.com.apps.churrascow.util.navigateTo
import kotlinx.coroutines.launch

/**
 * This class is responsible for searching in preferences if the user wants to save his data
 * and id.
 */
class StarterFragment : Fragment() {

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

        lifecycleScope.launch {
            requireContext().dataStore.data.collect { preferences ->

                val shouldRememberPassword =
                    preferences[rememberPassword] ?: defaultValueForRememberPassword

                if (shouldRememberPassword) {
                    searchForLoggedUser(preferences)
                } else {
                    startLoginFlowAgain()
                }
            }
        }

    }

    private fun searchForLoggedUser(preferences: Preferences) {
        preferences[userLogged]?.let {
            requireContext().navigateTo(MainActivity::class.java)
            requireActivity().finish()
        } ?: navigateToLogin()
    }

    private fun navigateToLogin() {
        Navigation.findNavController(requireView())
            .navigate(StarterFragmentDirections.actionStarterFragmentToNavLogin())
    }

    private suspend fun startLoginFlowAgain() {
        requireContext().dataStore.edit { preferences ->
            preferences.remove(userLogged)
            navigateToLogin()
        }
    }

}