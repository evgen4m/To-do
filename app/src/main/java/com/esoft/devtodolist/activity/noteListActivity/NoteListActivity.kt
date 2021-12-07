package com.esoft.devtodolist.activity.noteListActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esoft.devtodolist.activity.searchActivity.SearchActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsLoader
import com.esoft.devtodolist.databinding.ActivityMainBinding
import com.esoft.devtodolist.helpers.CREATE_NOTE
import com.esoft.devtodolist.helpers.DELETE_NOTE
import com.esoft.devtodolist.helpers.NO_SEARCH_NOTE
import com.esoft.devtodolist.model.NoteModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var notesAdapter: NoteAdapter
    lateinit var handler: Handler

    private lateinit var viewModel: NoteListViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NoteListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsLoader(this).getAppColor())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()

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

                    NO_SEARCH_NOTE -> {
                        binding.recyclerListNote.visibility = View.GONE
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
        viewModel.getNoteLiveData()?.observe(this, Observer<List<NoteModel>>() {
            //notesAdapter.setItems(it)
            notesAdapter.listOfNote = it as ArrayList<NoteModel>
            val message = Message.obtain()
            message.what = CREATE_NOTE
            handler.sendMessage(message)
        })

    }

    private fun onClick() {
        binding.searchView.apply {
            searchNote.setOnClickListener {
                val intent = Intent(this@NoteListActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            openSettings.setOnClickListener {
                viewModel.openSettings(this@NoteListActivity)
            }
        }
    }

}