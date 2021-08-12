package com.esoft.devtodolist.activity.newNoteActivity


import android.content.Context
import androidx.lifecycle.ViewModel
import com.esoft.devtodolist.app.App
import com.esoft.devtodolist.model.NoteModel

class NewNoteViewModel: ViewModel() {

    fun updateNote(noteModel: NoteModel) {
        App.getInstance().repositoryDao.update(noteModel)
    }

    fun insertNote(noteModel: NoteModel) {
        App.getInstance().repositoryDao.insert(noteModel)
    }

}