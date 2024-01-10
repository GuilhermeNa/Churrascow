package br.com.apps.churrascow.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "userSession")

val userLogged = stringPreferencesKey("user_logged")

val rememberPassword = booleanPreferencesKey("remember_password")

const val defaultValueForRememberPassword = true