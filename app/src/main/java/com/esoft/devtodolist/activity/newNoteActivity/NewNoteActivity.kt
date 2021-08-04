package com.esoft.devtodolist.activity.newNoteActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.R
import com.esoft.devtodolist.databinding.ActivityNewNoteBinding

class NewNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewNoteBinding
    private lateinit var viewModel: NewNoteViewModel

    companion object {
        fun start (context: Context) {
            val intent = Intent (context, NewNoteActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewNoteViewModel::class.java)

        setSupportActionBar(binding.toolbarNewNote)
        supportActionBar!!.title = null

        binding.toolbarNewNote.setNavigationIcon(R.drawable.ic_btn_back)
        binding.toolbarNewNote.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}