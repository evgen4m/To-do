package com.esoft.devtodolist.activity.settingsActivity

import android.os.Bundle
import android.preference.PreferenceFragment
import com.esoft.devtodolist.R

class SettingsFragment: PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }

}