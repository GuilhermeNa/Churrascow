package br.com.apps.churrascow.service

import br.com.apps.churrascow.exception.BlankStringException
import br.com.apps.churrascow.exception.InvalidEmailException
import br.com.apps.churrascow.exception.InvalidFormatException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.exception.StringTooBigException
import br.com.apps.churrascow.exception.StringTooSmallException

const val NO_TAG = "no_tag"

/**
 * Class responsible for validating whether a string is valid to be used as needed.
 *
 * First you define which string will be called using [forString] method and then you make chained
 * calls for each aspect you want to check.
 *
 * @throws ObjectNotFoundException
 * @throws InvalidFormatException
 * @throws BlankStringException
 *
 */
class ValidationService {

    private var validatedString: String? = null
    private var tagIdentifier: String? = null

    /**
     * First method to be called, it stores the string received in
     * a variable to be used by other methods.
     *
     * @throws ObjectNotFoundException
     */
    fun forString(string: String?): ValidationService {
        validatedString = null

        string?.let {
            val stringTrim = it.trim()
            validatedString = stringTrim
        } ?: throw ObjectNotFoundException(tagIdentifier ?: NO_TAG)
        return this
    }

    fun tagIdentifier(tag: String): ValidationService {
        tagIdentifier = null
        tagIdentifier = tag
        return this
    }

    /**
     * Check if string is blank.
     * @throws ObjectNotFoundException
     * @throws BlankStringException
     */
    fun cannotBeBlank(): ValidationService {
        validatedString?.let { string ->
            if (string.isBlank()) {
                throw BlankStringException(tagIdentifier ?: NO_TAG)
            }
        } ?: throw ObjectNotFoundException(tagIdentifier ?: NO_TAG)
        return this
    }

    /**
     * Check if [validatedString] size is bigger than [int].
     * @param int is the value that will be used for comparison.
     * @throws StringTooSmallException
     * @throws ObjectNotFoundException
     */
    fun cannotBeSmallerThan(int: Int): ValidationService {
        validatedString?.let { string ->
            if (string.length < int) {
                throw StringTooSmallException(tagIdentifier ?: NO_TAG)
            }
        } ?: throw ObjectNotFoundException(tagIdentifier ?: NO_TAG)
        return this
    }

    /**
     * Check if [validatedString] size is smaller than [int].
     * @param int is the value that will be used for comparison.
     * @throws StringTooBigException
     * @throws ObjectNotFoundException
     */
    fun cannotBeBiggerThan(int: Int): ValidationService {
        validatedString?.let { string ->
            if (string.length > int) {
                throw StringTooBigException(tagIdentifier ?: NO_TAG)
            }
        } ?: throw ObjectNotFoundException(tagIdentifier ?: NO_TAG)
        return this
    }

    /**
     * Check if [validatedString] is in an email format.
     * @throws InvalidEmailException
     * @throws ObjectNotFoundException
     */
    fun mustBeEmailFormat(): ValidationService {
        validatedString?.let { string ->
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
                throw InvalidEmailException(tagIdentifier ?: NO_TAG)
            }
        } ?: throw ObjectNotFoundException(tagIdentifier ?: NO_TAG)
        return this
    }

    fun result(): String {
        return validatedString!!
    }

}