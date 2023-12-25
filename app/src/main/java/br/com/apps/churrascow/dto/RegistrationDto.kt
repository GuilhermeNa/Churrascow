package br.com.apps.churrascow.dto

/**
 * This class is responsible for holding data inserted when user is trying to register himself.
 *
 * @param name
 * @param email Used as User's PrimaryKey.
 * @param password 6 to 12 numbers.
 * @param passwordConfirmation Must be the same as password, used for comparison and confirmation.
 */
class RegistrationDto(

    val name: String,
    val email: String,
    val password: String,
    private val passwordConfirmation: String

) {

    /**
     * Used to confirm if the password and password confirmation are equal.
     *
     * @return true - if the password and passConfirmation are the same.
     *
     * false - if the password and passConfirmation are the different.
     *
     */
    fun confirmPassword(): Boolean = password == passwordConfirmation

}