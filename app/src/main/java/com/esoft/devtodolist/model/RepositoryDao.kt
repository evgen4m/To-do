package com.esoft.devtodolist.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM NoteModel")
    fun getAllLiveData(): LiveData<List<NoteModel>>

    @Query("DELETE FROM NoteModel WHERE done = :done")
    fun deleteCompliteNote(done: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteModel)

    @Update
    fun update(note: NoteModel)

    @Delete
    fun delete(note: NoteModel)

}