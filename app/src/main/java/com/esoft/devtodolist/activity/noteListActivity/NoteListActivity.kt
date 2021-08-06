package com.esoft.devtodolist.activity.noteListActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.R
import com.esoft.devtodolist.databinding.ActivityMainBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.searchBar)
        supportActionBar!!.title = null

        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)

        binding.floatingActionButton.setOnClickListener {
            viewModel.openNewNoteScreen(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.getAllSettings -> {
                viewModel.openSettings(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}