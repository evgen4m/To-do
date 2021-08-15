package com.esoft.devtodolist.activity.noteListActivity

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsActivity
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository


class NoteListViewModel(application: Application): AndroidViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)

    fun getNoteLiveData(): LiveData<List<NoteModel>>? = repository.getNoteLiveData()

    fun openNewNoteScreen(context: Context) {
        NewNoteActivity().start(context as Activity, null)
    }

    fun openSettings(context: Context) {
        SettingsActivity.start(context)
    }


}