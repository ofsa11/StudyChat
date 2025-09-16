package com.example.studychat.utils

import android.content.Context

class UserManageUtils(context: Context){

    fun Register(username: String,password: String){
        PreferenceUtils.putString("username", username)
        PreferenceUtils.putString("password", password)
    }

    fun Login(username: String,password: String): Boolean{
        val savedUsernam = PreferenceUtils.getString("username")
        val savedPassword = PreferenceUtils.getString("password")
        if(username == savedUsernam && password == savedPassword){
            PreferenceUtils.putBoolean("isLogin", true)
            return true
        }
        else{
            return false
        }
    }

    fun Logout(){
        PreferenceUtils.putBoolean("isLogin", false)
    }

}