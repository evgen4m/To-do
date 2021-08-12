package com.esoft.devtodolist.activity.noteListActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esoft.devtodolist.R
import com.esoft.devtodolist.base.CREATE_NOTE
import com.esoft.devtodolist.base.DELETE_NOTE
import com.esoft.devtodolist.databinding.ActivityMainBinding
import com.esoft.devtodolist.model.NoteModel

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteListViewModel
    lateinit var notesAdapter: NoteAdapter

    lateinit var handler: Handler

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NoteListActivity::class.java)
            context.startActivity(intent)
        }
    }

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

        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    DELETE_NOTE -> {
                        binding.recyclerListNote.visibility = View.GONE
                        binding.linearBackgroundNoNote.visibility = View.VISIBLE
                    }
                    CREATE_NOTE -> {
                        binding.recyclerListNote.visibility = View.VISIBLE
                        binding.linearBackgroundNoNote.visibility = View.GONE
                    }

                }
            }
        }


        notesAdapter = NoteAdapter(handler)
        val lm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerListNote.layoutManager = lm
        binding.recyclerListNote.adapter = notesAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNoteLiveData().observe(this, Observer<List<NoteModel>>() {
            notesAdapter.setItems(it)
            val message = Message.obtain()
            message.what = CREATE_NOTE
            handler.sendMessage(message)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.getAllSettings -> {
                viewModel.openSettings(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}