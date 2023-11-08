package com.vynguyen.room.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.vynguyen.room.database.NoteDatabase
import com.vynguyen.room.database.dao.NoteDao
import com.vynguyen.room.model.Note

class NoteRepository(app: Application) {

    private var noteDao: NoteDao

    init {
        val noteDatabase = NoteDatabase.getInstance(app)
        noteDao = noteDatabase.getNoteDao()
    }

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)

    fun getAllNote(): LiveData<List<Note>> = noteDao.getAllNote()
    fun searchByTitle(title: String): LiveData<List<Note>> = noteDao.searchByTitle(title)

    fun searchByDescription(title: String): LiveData<List<Note>> =
        noteDao.searchByDescription(title)

    fun searchByTitleAndDes(title: String, des: String): LiveData<List<Note>> =
        noteDao.searchByTitleAndDes(title, des)
}