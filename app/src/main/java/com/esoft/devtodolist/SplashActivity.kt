package com.esoft.devtodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esoft.devtodolist.activity.noteListActivity.NoteListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, NoteListActivity::class.java)
        startActivity(intent)
        finish()
    }
}