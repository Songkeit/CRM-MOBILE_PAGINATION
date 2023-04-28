package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreManager(context:Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "KEY_LIST_DELETE")
    private val dataStore = context.dataStore

   companion object{
       val deleteModeKey = booleanPreferencesKey("DELETE_MODE_KEY")

   }
    suspend fun setDelete(isDelete:Boolean){
        dataStore.edit { pref ->
            pref[deleteModeKey] = isDelete
        }
    }
    fun getDelete(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException){
                    emit(emptyPreferences())
                }
                else{
                    throw exception
                }
            }.map { pref ->
                val uiMode = pref[deleteModeKey] ?: false
                uiMode

            }
    }
}