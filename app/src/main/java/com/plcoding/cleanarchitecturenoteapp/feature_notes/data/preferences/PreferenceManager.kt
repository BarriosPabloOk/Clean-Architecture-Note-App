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
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.DataStoreNoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.DataStoreOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder

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
        val NOTE_ORDER = stringPreferencesKey("note_order")
        val ORDER_TYPE = stringPreferencesKey("order_type")


    }
    suspend fun saveInDataStore(noteOrder: String, orderType:String){
        context.dataStore.edit {
            it[PreferenceKeys.NOTE_ORDER] = noteOrder
            it[PreferenceKeys.ORDER_TYPE] = orderType

        }
    }

    val readDataStore: Flow<DataStoreOrder> = context.dataStore.data
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

            val noteOrder = preference[PreferenceKeys.NOTE_ORDER] ?: DataStoreNoteOrder.DATE.name
            val orderType = preference[PreferenceKeys.ORDER_TYPE] ?: DataStoreNoteOrder.DESCENDING.name

            DataStoreOrder(noteOrder, orderType)
    }



}