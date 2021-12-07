package com.esoft.devtodolist.activity.noteListActivity

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.esoft.devtodolist.activity.aboutActivity.AboutActivity
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsActivity
import com.esoft.devtodolist.databinding.SearchViewBinding
import com.esoft.devtodolist.helpers.CREATE_NOTE
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest


class NoteListViewModel(application: Application): AndroidViewModel(application) {

    private val repository = NoteRepository(application = application)

    fun getNoteLiveData(): LiveData<List<NoteModel>>? = repository.getNoteLiveData()

    fun openNewNoteScreen(context: Context) {
        NewNoteActivity.start(context as Activity, null)
    }

    fun deleteCompliteNote() {
        repository.deleteDoneNote(true)
    }

    fun openAboutScreen(context: Context) {
        AboutActivity.start(context as Activity)
    }

    fun openSettings(context: Context) {
        SettingsActivity.start(context as Activity)
    }
}
