package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.content.Context
import android.util.Log
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharePreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefsTypeStory = context.getSharedPreferences("PREFS_TOKEN_FILE",Context.MODE_PRIVATE)
    private var prefsServiceList = context.getSharedPreferences("PREFS_TOKEN_FILE_SERVICE",Context.MODE_PRIVATE)
    private var prefsStateDelete = context.getSharedPreferences("PREFS_TOKEN_FILE_DELETE",Context.MODE_PRIVATE)

    private var gson = Gson()
    fun savePreferenceTypeStory(items: TypeStoryModel?){
        Log.i("itemssss", "savePreference: $items")
        var editor = prefsTypeStory.edit()
//        editor.putString(USER_ITEM,items)
        val jsonString = gson.toJson(items)
        Log.i("json", "savePreference: $jsonString")
        editor.putString("userItems",jsonString)
        editor.apply()

    }
    fun saveStateDelete(state: String?){
        var editor = prefsStateDelete.edit()
////        editor.putString(USER_ITEM,items)
//        val jsonString = gson.toJson(state)
//        Log.i("json", "savePreference: $jsonString")
        editor.putString("userItemsDelete",state)
        editor.apply()

    }

    fun savePreferenceServiceList(items: ServiceList?){
        Log.i("itemssss", "savePreference: $items")
        var editor = prefsServiceList.edit()
//        editor.putString(USER_ITEM,items)
        val jsonString = gson.toJson(items)
        Log.i("json", "savePreference: $jsonString")
        editor.putString("userItemsService",jsonString)
        editor.apply()

    }
    fun getPreferenceService(): String? {
        return prefsServiceList.getString("userItemsService",null)
    }

    fun getPreferenceTypeStory(): String? {
        return prefsTypeStory.getString("userItems",null)
    }

    fun getStateDelete(): String? {
        return prefsStateDelete.getString("userItemsDelete",null)
    }


}