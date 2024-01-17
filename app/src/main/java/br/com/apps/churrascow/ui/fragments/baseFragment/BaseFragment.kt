package br.com.apps.churrascow.ui.fragments.baseFragment

import android.view.View
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.preferences.dataStore
import br.com.apps.churrascow.preferences.userLogged
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Base fragment witch contains common actions for Fragments.
 */
abstract class BaseFragment : Fragment() {

    //---------------------------------------------------------------------------------------------//
    // PUBLIC METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Defines a timer range for click interactions with the view.
     *
     * @param view that receives the timer.
     * @param rangeTimer range time in milliseconds.
     */
    fun setClickRangeTimer(view: View?, rangeTimer: Long) {
        view?.let {
            lifecycleScope.launch {
                it.isEnabled = false
                delay(rangeTimer)
                it.isEnabled = true
            }
        }
    }

    /**
     * Check if the strings in the fields are good.
     *
     * @throws InvalidFormatException when it's not good
     */
    fun validateStringOfField(text: String) {
        if (text.isBlank()) {
            throw InvalidFormatException("Field is empty")
        }
    }

}