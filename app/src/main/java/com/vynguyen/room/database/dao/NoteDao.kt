package com.vynguyen.room.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vynguyen.room.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from note_table")
    fun getAllNote() : LiveData<List<Note>>

    @Query("select * from note_table where title_col=:title")
    fun searchByTitle(title: String): LiveData<List<Note>>

    @Query("select * from note_table where description_col=:description")
    fun searchByDescription(description: String): LiveData<List<Note>>

    @Query("select * from note_table where title_col=:title and description_col=:des")
    fun searchByTitleAndDes(title: String, des: String): LiveData<List<Note>>
}