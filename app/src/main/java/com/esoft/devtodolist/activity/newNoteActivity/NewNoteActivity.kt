package com.esoft.devtodolist.activity.newNoteActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.R
import com.esoft.devtodolist.app.App
import com.esoft.devtodolist.databinding.ActivityNewNoteBinding
import com.esoft.devtodolist.model.NoteModel

class NewNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewNoteBinding
    private lateinit var viewModel: NewNoteViewModel
    lateinit var note: NoteModel

    companion object {
        const val EXTRA_NOTES = "NotesDetailActivity.EXSTRA_NOTE"
    }

    fun start(caller: Activity, note: NoteModel?) {
        val intent = Intent (caller, NewNoteActivity::class.java)
        if(note != null) {
            intent.putExtra(EXTRA_NOTES, note)
        }
        caller.startActivity(intent)
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

        if(intent.hasExtra(EXTRA_NOTES)) {
            note = intent.getParcelableExtra(EXTRA_NOTES)!!
            binding.textNotesTitle.setText(note.textHead)
            binding.noteText.setText(note.text)
            println(note.textHead)
        }else {
            note = NoteModel()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.saveNote -> {
                if(binding.textNotesTitle.text!!.isNotEmpty()) {
                    note.textHead = binding.textNotesTitle.text.toString()
                    note.text = binding.noteText.text.toString()
                    note.done = false
                    note.timestamp = System.currentTimeMillis()
                    if (intent.hasExtra(EXTRA_NOTES)) {
                        viewModel.updateNote(noteModel = note)
                    }else {
                        viewModel.insertNote(noteModel = note)
                    }
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}