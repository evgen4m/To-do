package com.esoft.devtodolist.activity.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.activity.noteListActivity.NoteListActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        viewModel.closeStarting(this)
        finish()
    }
}