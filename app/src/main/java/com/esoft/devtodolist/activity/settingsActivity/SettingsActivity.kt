package com.esoft.devtodolist.activity.settingsActivity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.esoft.devtodolist.R
import com.esoft.devtodolist.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {

    private lateinit var settingsBinding: ActivitySettingsBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsLoader(this).getAppColor())
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)
        setSupportActionBar(settingsBinding.toolbarSettingsActivity)
        supportActionBar!!.title = getString(R.string.settingsActivity)

        settingsBinding.toolbarSettingsActivity.setNavigationIcon(R.drawable.ic_btn_back)
        settingsBinding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        //fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
        fragmentManager.beginTransaction().add(R.id.fragmentContainer1, SettingsFragment()).commit()
        //fragmentManager.beginTransaction().add(R.id.fragmentContainer2, ThemeFragment()).commit()
    }
}