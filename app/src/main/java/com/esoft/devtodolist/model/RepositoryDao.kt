package com.esoft.devtodolist.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM NoteModel")
    fun getAll(): List<NoteModel>

    @Query("SELECT * FROM NoteModel")
    fun getAllLiveData(): LiveData<List<NoteModel>>

    @Query("SELECT * FROM NoteModel WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE id = :uid LIMIT 1")
    fun findById(uid: Int): NoteModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteModel)

    @Update
    fun update(note: NoteModel)

    @Delete
    fun delete(note: NoteModel)

}