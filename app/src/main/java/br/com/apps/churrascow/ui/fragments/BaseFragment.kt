package br.com.apps.churrascow.ui.fragments

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Base fragment witch contains common actions for Fragments.
 */
abstract class BaseFragment : Fragment() {

    /**
     * Defines a range timer for click interactions with the view.
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

}