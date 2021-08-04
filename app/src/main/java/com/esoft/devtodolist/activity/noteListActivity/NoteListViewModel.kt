package com.esoft.devtodolist.activity.noteListActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.activity.settingsActivity.SettingsActivity

class NoteListViewModel: ViewModel() {

    fun openNewNoteScreen(context: Context) {
        NewNoteActivity.start(context = context)
    }

    fun openSettings(context: Context) {
        SettingsActivity.start(context)
    }

}