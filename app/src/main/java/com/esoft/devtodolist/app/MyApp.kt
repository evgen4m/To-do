package com.esoft.devtodolist.app

import android.app.Application
import androidx.room.Room
import com.esoft.devtodolist.base.AppDataBase
import com.esoft.devtodolist.model.RepositoryDao

class MyApp : Application() {

    private var appDataBase: AppDataBase? = null
    var noteDao: RepositoryDao? = null
    private var instance: MyApp? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDataBase =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, "app-db-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        noteDao = appDataBase!!.repositoryDao()
    }


}