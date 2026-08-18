package com.code4galaxy.ecommerceapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.code4galaxy.ecommerceapp.response.User

class SessionManager(val context: Context) {
   private val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    companion object{
        private const val USER_ID = "user_id"
        private const val FULL_NAME = "full_name"
        private const val MOBILE_NO = "mobile_no"
        private const val EMAIL_ID = "email_id"
        private const val IS_LOGGED_IN = "is_logged_in"
    }
    fun saveUser(user: User){
        sharedPreferences.edit()
            .putString(USER_ID,user.userId)
            .putString(FULL_NAME,user.fullName)
            .putString(MOBILE_NO,user.mobileNo)
            .putString(EMAIL_ID,user.emailId)
            .putBoolean(IS_LOGGED_IN,true)
            .apply()
    }
    fun getUserId(): String?{
        return sharedPreferences.getString(USER_ID,null)
    }
    fun getFullName(): String?{
        return sharedPreferences.getString(FULL_NAME,null)
    }
    fun getMobileNo(): String?{
        return sharedPreferences.getString(MOBILE_NO,null)
    }
    fun getEmail(): String?{
        return sharedPreferences.getString(EMAIL_ID,null)
    }
    fun getIsLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false)
    }
    fun clearSession(){
        sharedPreferences.edit()
            .clear()
            .apply()
    }
    fun saveLatestOrderId(orderId: Int) {
        sharedPreferences.edit()
            .putInt("latest_order_id", orderId)
            .apply()
    }

    fun getLatestOrderId(): Int {
        return sharedPreferences.getInt(
            "latest_order_id",
            0
        )
    }
}