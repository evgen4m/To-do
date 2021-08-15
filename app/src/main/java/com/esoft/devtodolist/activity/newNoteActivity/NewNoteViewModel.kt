package com.esoft.devtodolist.activity.newNoteActivity


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository

class NewNoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NoteRepository = NoteRepository(application)
    val noteModel = MutableLiveData<NoteModel>()


    /*init {
        val noteModel = repository.getFromId(noteId)
        if (noteModel != null) {
            this.noteModel.value = noteModel
        }
    }*/

    fun updateNote(noteModel: NoteModel) {
       repository.update(noteModel = noteModel)
    }

    fun insertNote(noteModel: NoteModel) {
        repository.insert(noteModel = noteModel)
    }

}