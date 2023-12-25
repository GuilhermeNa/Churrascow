package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.exception.InvalidFormatException

const val EMPTY_PASSWORD = "Preencha a senha"
const val BAD_PASSWORD = "Senha deve ter no mínimo 6 caracteres"
const val UNCONFIRMED_PASSWORD = "Senha não confere"

const val EMPTY_EMAIL = "Preencha o email"
const val BAD_EMAIL = "Email inválido"

const val EMPTY_NAME = "Preencha o nome"

const val NO_ERROR = ""

class CredentialUseCase {

    /**
     * Checking credentials and throw exceptions if there is any errors in Login.
     * Fields cannot be blank. Email must be in an expected format(a@b.c).
     * Password must be between 6 and 12 characters.
     *
     *@param credentials contains Login(email) and Password.
     *
     * @throws InvalidFormatException If there is any bad field.
     *
     * email - being empty or in a bad format for email.
     *
     * password - smaller then 6 or don't matching with confirmation.
     */
    fun loginCheck(credentials: Pair<String, String>) {
        val emailCheck = checkIfEmailIsGood(credentials.first)
        val passwordCheck = checkIfPasswordIsGood(credentials.second)

        if (!emailCheck.first || !passwordCheck.first) {
            val error = "${emailCheck.second}, ${passwordCheck.second}"
            throw InvalidFormatException(error)
        }

    }

    private fun checkIfEmailIsGood(email: String): Pair<Boolean, String> {
        var error = Pair(true, NO_ERROR)

        if (email.isBlank()) {
            error = Pair(false, EMPTY_EMAIL)

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error = Pair(false, BAD_EMAIL)

        }

        return error
    }

    private fun checkIfPasswordIsGood(password: String): Pair<Boolean, String> {
        var error = Pair(true, NO_ERROR)

        if (password.isBlank()) {
            error = Pair(false, EMPTY_PASSWORD)

        } else if (password.length < 6) {
            error = Pair(false, BAD_PASSWORD)

        }

        return error
    }

    /**
     * Checking data received from view and throw exceptions if there is any errors when Register a new User.
     *
     *@param registrationDto contains the data from View.
     *
     * @throws InvalidFormatException If there is any bad field.
     *
     * name - being empty.
     *
     * email - being empty or in a bad format for email.
     *
     * password - smaller then 6 or don't matching with confirmation.
     */
    fun registrationCheck(registrationDto: RegistrationDto) {
        val nameCheck = checkIfNameIsGood(registrationDto.name)
        val emailCheck = checkIfEmailIsGood(registrationDto.email)
        val passwordCheck = checkIfPasswordIsGoodAndConfirmed(registrationDto)

        if (!nameCheck.first || !emailCheck.first || !passwordCheck.first) {
            val error = "${nameCheck.second}, ${emailCheck.second}, ${passwordCheck.second}"
            throw InvalidFormatException(error)
        }
    }

    private fun checkIfPasswordIsGoodAndConfirmed(registrationDto: RegistrationDto): Pair<Boolean, String> {
        var error = Pair(true, NO_ERROR)

        if (registrationDto.password.isBlank()) {
            error = Pair(false, EMPTY_PASSWORD)

        } else if (registrationDto.password.length < 6) {
            error = Pair(false, BAD_PASSWORD)

        } else if (!registrationDto.confirmPassword()) {
            error = Pair(false, UNCONFIRMED_PASSWORD)

        }

        return error
    }

    private fun checkIfNameIsGood(name: String): Pair<Boolean, String> {
        var error = Pair(true, NO_ERROR)

        if (name.isBlank()) {
            error = Pair(false, EMPTY_NAME)
        }

        return error
    }

}