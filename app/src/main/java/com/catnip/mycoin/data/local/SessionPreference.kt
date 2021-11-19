package com.catnip.mycoin.data.local;

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SessionPreference @Inject constructor(@ApplicationContext context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "MyCoinPreference" //app name or else
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_AUTH_TOKEN = Pair("PREF_AUTH_TOKEN", null)
    }

    var authToken: String?
        get() = preference.getString(PREF_AUTH_TOKEN.first, PREF_AUTH_TOKEN.second)
        set(value) = preference.edit {
            it.putString(PREF_AUTH_TOKEN.first, value)
        }

    fun deleteSession(){
        preference.delete()
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

private fun SharedPreferences.delete() {
    edit().clear().apply()
}
