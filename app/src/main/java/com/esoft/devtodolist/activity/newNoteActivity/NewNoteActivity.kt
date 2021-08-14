package com.esoft.devtodolist.activity.newNoteActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.esoft.devtodolist.R
import com.esoft.devtodolist.databinding.ActivityNewNoteBinding
import com.esoft.devtodolist.fragments.CalendarDialogFragment
import com.esoft.devtodolist.fragments.SupportInterface
import com.esoft.devtodolist.model.NoteModel
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity(), SupportInterface {

    lateinit var binding: ActivityNewNoteBinding
    private lateinit var viewModel: NewNoteViewModel
    lateinit var note: NoteModel
    private var dateF: String? = null
    private var time: String?= null

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
        val dialog = CalendarDialogFragment()

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
            println(note.dataCalendar)
            binding.buttonDate.text = note.dataCalendar
        }else {
            note = NoteModel()
            dateF = getDate()
            binding.buttonDate.text = dateF
        }

        binding.buttonDate.setOnClickListener {
            dialog.show(supportFragmentManager, "ss")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.saveNote -> {
                if(binding.textNotesTitle.text!!.isNotEmpty()) {
                    note.textHead = binding.textNotesTitle.text.toString()
                    note.text = binding.noteText.text.toString()
                    note.dataCalendar = binding.buttonDate.text.toString()
                    note.done = false
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

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val sdf = SimpleDateFormat("dd.M.yyyy")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    override fun getDate(date: String) {
        this.dateF = date
        binding.buttonDate.text = dateF
    }

    override fun getTime(time: String) {
        this.time = time
    }
}