package com.esoft.devtodolist.activity.settingsActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.R
import com.esoft.devtodolist.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    companion object {
        fun start (context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSettings)
        supportActionBar!!.title = getString(R.string.settingsActivity)

        binding.toolbarSettings.setNavigationIcon(R.drawable.ic_btn_back)
        binding.toolbarSettings.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
    }
}