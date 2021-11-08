package com.esoft.devtodolist.activity.aboutActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esoft.devtodolist.R
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsLoader
import com.esoft.devtodolist.databinding.ActivityAboutBinding
import com.esoft.devtodolist.helpers.EXTRA_NOTES

class AboutActivity : AppCompatActivity() {

    private lateinit var aboutBinding: ActivityAboutBinding

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsLoader(this).getAppColor())
        super.onCreate(savedInstanceState)
        aboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(aboutBinding.root)

        setSupportActionBar(aboutBinding.toolbarAboutActivity)
        supportActionBar!!.title = null

        aboutBinding.toolbarAboutActivity.setNavigationIcon(R.drawable.ic_btn_back)
        aboutBinding.toolbarAboutActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}