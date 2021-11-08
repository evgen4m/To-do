package com.esoft.devtodolist.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esoft.devtodolist.helpers.DATA_BASE_NAME
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.RepositoryDao

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao?

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            DATA_BASE_NAME
                        ).allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

    }

}