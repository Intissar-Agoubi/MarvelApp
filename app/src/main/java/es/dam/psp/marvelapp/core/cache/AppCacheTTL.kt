package es.dam.psp.marvelapp.core.cache

import android.content.Context
import es.dam.psp.marvelapp.R
import es.dam.psp.marvelapp.core.time.AppTime

class AppCacheTTL(private val context: Context , private val appTime: AppTime): AppCache {
    val sharedPref = context.getSharedPreferences(context.getString(R.string.app_cache), Context.MODE_PRIVATE)
    override fun updateTime(key: String) {
        val editor = sharedPref.edit()
        editor.putLong(key,appTime.currentTimeToLong()).apply()
    }

    override fun isExpired(key: String, ttl: Long): Boolean {
        val timeSaved = sharedPref.getLong(key,-1)
        return appTime.currentTimeToLong()-timeSaved >= ttl


    }
}