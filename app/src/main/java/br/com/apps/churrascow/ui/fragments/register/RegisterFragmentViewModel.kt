package br.com.apps.churrascow.ui.fragments.register

import androidx.lifecycle.ViewModel
import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.useCase.CredentialUseCase
import br.com.apps.churrascow.useCase.UserUseCase

class RegisterFragmentViewModel(

    private val userUseCase: UserUseCase,
    private val credentialUseCase: CredentialUseCase

) : ViewModel() {

    /**
     * This method is checking if there is any error and then, create a new object if there is no errors.
     *
     * @param registrationDto contains data received from the view.
     *
     * @return a triple of strings representing respectively the fields<name, email, password> of view.
     * Each field represents its error.
     *
     * Observe: if the string has NO_ERROR = "" as value, means that the field have been correctly
     * filed.
     */
    suspend fun registerBtnClicked(registrationDto: RegistrationDto): Triple<String, String, String>? {
        checkIfFieldsAreGood(registrationDto)?.let { exceptionTriple ->
            return exceptionTriple

        } ?: userUseCase.newUser(registrationDto)
        return null
    }

    private fun checkIfFieldsAreGood(registrationDto: RegistrationDto): Triple<String, String, String>? {
        try {
            credentialUseCase.registrationCheck(registrationDto)

        } catch (error: InvalidFormatException) {
            val errorReplaced = error.toString().replaceFirst(".*?:".toRegex(), "")
            val errorArray = errorReplaced.split(",")
            return Triple(
                errorArray[0],
                errorArray[1],
                errorArray[2]
            )
        }

        return null
    }


}