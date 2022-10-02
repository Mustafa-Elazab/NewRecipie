package com.example.newrecipie.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object AppPreference {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    fun initAppPreference(application: Application) {

        sharedPreferences = application.getSharedPreferences("APP_PREFERENCE", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }


    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearAll(): Boolean {
        return try {
            sharedPreferences.edit().clear().commit()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}