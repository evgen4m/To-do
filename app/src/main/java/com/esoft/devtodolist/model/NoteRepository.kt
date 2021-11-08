package com.esoft.devtodolist.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.esoft.devtodolist.base.AppDataBase

class NoteRepository (application: Application) {

    private var repositoryDao: RepositoryDao? = null

    init {
        val db = AppDataBase.getDataBase(application)
        repositoryDao = db?.repositoryDao()
    }

    fun getNoteLiveData(): LiveData<List<NoteModel>>? = repositoryDao?.getAllLiveData()

    fun deleteDoneNote(done: Boolean) {
        repositoryDao?.deleteCompliteNote(done = done)
    }

    fun update(noteModel: NoteModel) {
        repositoryDao?.update(note = noteModel)
    }

    fun insert(noteModel: NoteModel) {
        repositoryDao?.insert(note = noteModel)
    }

    fun delete(noteModel: NoteModel) {
        repositoryDao?.delete(note = noteModel)
    }

}