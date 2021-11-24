package com.plcoding.cleanarchitecturenoteapp.feature_notes.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

import javax.inject.Inject
import javax.inject.Singleton


const val PREFERENCES_NAME = "note_app_pref"
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class PreferenceManager @Inject constructor(
    val context : Context,
) {



    private object PreferenceKeys {
        val ORDER_TYPE = stringPreferencesKey("order_type")
        val NOTE_ORDER = stringPreferencesKey("note_order")

    }
    suspend fun saveInDataStore(orderType:String){
        context.dataStore.edit {
            it[PreferenceKeys.ORDER_TYPE] = orderType

        }
    }

    val readDataStore: Flow<String> = context.dataStore.data
        .catch {
            exception ->
            if (exception is IOException){
                Log.e("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {preference ->

            val pref = preference[PreferenceKeys.ORDER_TYPE] ?: "Descending"

            pref
    }



}