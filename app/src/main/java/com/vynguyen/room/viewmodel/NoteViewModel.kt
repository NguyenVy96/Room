package com.vynguyen.room.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vynguyen.room.database.repository.NoteRepository
import com.vynguyen.room.model.Note
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class NoteViewModel(app: Application) : ViewModel() {

    private val noteRepository = NoteRepository(app)

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        noteRepository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }

    fun getAllNote(): LiveData<List<Note>> = noteRepository.getAllNote()

    fun searchByTitle(title: String): LiveData<List<Note>> = noteRepository.searchByTitle(title)

    fun searchByDescription(des: String): LiveData<List<Note>> =
        noteRepository.searchByDescription(des)

    fun searchByTitleAndDes(title: String, des: String): LiveData<List<Note>> =
        noteRepository.searchByTitleAndDes(title, des)


    class NoteViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                return NoteViewModel(app) as T
            }
            throw IllegalAccessException("Unable constructor viewModel")
        }

    }
}