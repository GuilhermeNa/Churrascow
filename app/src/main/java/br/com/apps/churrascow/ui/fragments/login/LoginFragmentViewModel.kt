package br.com.apps.churrascow.ui.fragments.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.useCase.CredentialUseCase
import br.com.apps.churrascow.useCase.UserUseCase
import br.com.apps.churrascow.util.TAG

class LoginFragmentViewModel(

    private val userUseCase: UserUseCase,
    private val loginUseCase: CredentialUseCase

) : ViewModel() {

    /**
     * Is responsible for telling the UI when the login panel should be displayed or not
     *
     * true - display the login panel.
     *
     * false - hide the login panel
     */
    private var _loginPanel = MutableLiveData(false)
    val loginPanel get() = _loginPanel

    /**
     * Hold the actual state of remember password checkbox.
     *
     * true - user wants to hold user and password.
     *
     * false - user don't want to hold user and password.
     */
    var rememberPassword: Boolean = true

    //---------------------------------------------------------------------------------------------//
    // METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Set mutableLiveData [_loginPanel], responsible for login panel equals true.
     *
     * @see backBtnCLicked
     */
    fun accessBtnCLicked() {
        _loginPanel.value = true
    }

    /**
     * Set mutableLiveData [_loginPanel], responsible for login panel equals false.
     *
     * @see accessBtnCLicked
     */
    fun backBtnCLicked() {
        _loginPanel.value = false
    }

    /**
     * Alter the [rememberPassword] state between true and false while the user interacts with the view.
     *
     * @see rememberPasswordInitialState
     */
    fun checkBoxClicked(isChecked: Boolean) {
        rememberPassword = isChecked
    }

    /**
     * Defines the initial state of [rememberPassword] received by dataStore.
     *
     * @see checkBoxClicked
     */
    fun rememberPasswordInitialState(rememberPass: Boolean?) {
        rememberPass?.let {
            rememberPassword = it
        }
    }

    /**
     * This method is responsible for call an UseCase for checking credentials. If any exceptions
     * is generated, create a Pair containing the errors and send it back to Ui with the error.
     *
     * @param credentials contains Login and Password.
     *
     * @return A pair of strings, the first string represents login edit text error
     * and the second represents password errors.
     */
    fun checkCredentials(credentials: Pair<String, String>): Pair<String, String>? {
        try {
            loginUseCase.loginCheck(credentials)

        } catch (error: InvalidFormatException) {
            val errorReplaced = error.toString().replaceFirst(".*?:".toRegex(), "")
            val errorArray = errorReplaced.split(",")
            return Pair(
                errorArray[0],
                errorArray[1]
            )
        }

        return null
    }

    /**
     * Flow for authenticate an user.
     *
     * @param credentials is a pair which contain login and password.
     *
     *@return User?
     */
    suspend fun authenticate(credentials: Pair<String, String>): User? {
        return userUseCase.authenticate(credentials)
    }

}