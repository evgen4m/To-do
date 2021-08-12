package com.esoft.devtodolist.activity.noteListActivity

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsActivity
import com.esoft.devtodolist.app.App
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.RepositoryDao

class NoteListViewModel: ViewModel() {

    private val noteLiveData: LiveData<List<NoteModel>> = App.getInstance().repositoryDao.getAllLiveData()

    fun getNoteLiveData(): LiveData<List<NoteModel>> {
        return noteLiveData
    }

    fun openNewNoteScreen(context: Context) {
        NewNoteActivity().start(context as Activity, null)
    }

    fun openSettings(context: Context) {
        SettingsActivity.start(context)
    }


}