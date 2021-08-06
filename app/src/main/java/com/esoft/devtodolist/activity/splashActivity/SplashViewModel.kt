package com.esoft.devtodolist.activity.splashActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.esoft.devtodolist.activity.noteListActivity.NoteListActivity

class SplashViewModel: ViewModel() {

    fun closeStarting(context: Context) {
        NoteListActivity.start(context = context)
    }

}