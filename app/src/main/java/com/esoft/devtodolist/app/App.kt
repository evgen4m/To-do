package com.esoft.devtodolist.app

import android.app.Application
import androidx.room.Room
import com.esoft.devtodolist.base.AppDataBase
import com.esoft.devtodolist.model.RepositoryDao

class App: Application() {

    private var appDataBase: AppDataBase? = null
    private var noteDao: RepositoryDao? = null

    private var instance: App? = null

    fun getInstance(): App? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDataBase =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, "app-db-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        noteDao = appDataBase!!.noteDao()
    }

}