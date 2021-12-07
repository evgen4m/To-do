package com.esoft.devtodolist.activity.searchActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val repository = NoteRepository(application = application)

    fun getNoteLiveData(): LiveData<List<NoteModel>>? = repository.getNoteLiveData()

}