package br.com.apps.churrascow.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "userSession")

/**
 * Holds the reference of the user with active session.
 */
val userLogged = stringPreferencesKey("user_logged")

/**
 * Defines whether the user wants to save their login and password and maintain logged in.
 */
val rememberPassword = booleanPreferencesKey("remember_password")

/**
 * Used when the user is using the app for the first time.
 */
const val defaultValueForRememberPassword = false