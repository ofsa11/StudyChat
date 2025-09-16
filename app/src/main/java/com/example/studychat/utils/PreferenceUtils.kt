package com.example.studychat.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private val PREF_NAME = "app_prefs"
    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String?) {
        sp.edit().putString(key,value).apply()
    }

    fun getString(key: String, defValue: String? = null): String? {
        return sp.getString(key, defValue)
    }

    fun putBoolean(key: String,value: Boolean){
        sp.edit().putBoolean(key,value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defValue)
    }
}