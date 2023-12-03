package com.angelika.mynotes.data.preferencies

import android.content.Context

class PreferenceHelper(context: Context) {
    val sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var isAuthentication
        set(value) = sharedPreferences.edit().putBoolean("Bool", value).apply()
        get() = sharedPreferences.getBoolean("Bool",false)
}