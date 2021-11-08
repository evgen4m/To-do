package com.esoft.devtodolist.activity.settingsActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.esoft.devtodolist.R

class SettingsLoader(val context: Context) {

    private var myPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Загрузка настроек цвета
     */

    @SuppressLint("ResourceAsColor")
    fun getAppColor(): Int {
        return when(myPref.getString("app_color_pref", "Голубая")) {
            "Голубой" -> {
               R.style.Theme_DevTodoList_Blue
            }
            "Розовый" -> {
                R.style.Theme_DevTodoList_Pink
            }
            "Зеленый" -> {
                R.style.Theme_DevTodoList_Green
            }
            else -> R.style.Theme_DevTodoList_Blue
        }
    }
}