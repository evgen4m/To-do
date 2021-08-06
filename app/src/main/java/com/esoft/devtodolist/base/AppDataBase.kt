package com.esoft.devtodolist.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.RepositoryDao

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun noteDao(): RepositoryDao?

}