package com.esoft.devtodolist.activity.searchActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esoft.devtodolist.R
import com.esoft.devtodolist.activity.noteListActivity.NoteAdapter
import com.esoft.devtodolist.activity.noteListActivity.NoteListViewModel
import com.esoft.devtodolist.activity.settingsActivity.SettingsLoader
import com.esoft.devtodolist.databinding.ActivitySearchBinding
import com.esoft.devtodolist.helpers.CREATE_NOTE
import com.esoft.devtodolist.helpers.DELETE_NOTE
import com.esoft.devtodolist.helpers.NO_SEARCH_NOTE
import com.esoft.devtodolist.model.NoteModel
import android.text.Editable

import android.text.TextWatcher
import android.widget.Toast


class SearchActivity : AppCompatActivity() {

    private lateinit var searchBinding: ActivitySearchBinding
    private lateinit var notesAdapter: NoteAdapter
    lateinit var handler: Handler

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsLoader(this).getAppColor())
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchBinding.btnBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
            override fun handleMessage(msg: Message) {
                when (msg.what) {

                }
            }
        }

        notesAdapter = NoteAdapter(handler)
        val lm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        searchBinding.recyclerViewSearch.layoutManager = lm
        searchBinding.recyclerViewSearch.adapter = notesAdapter

        searchBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                notesAdapter.filter.filter(s.toString())
            }
        })



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
}