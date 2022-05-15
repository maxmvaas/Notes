package ru.maxmv.notes.presentation.note_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.data.NoteRepository
import ru.maxmv.notes.data.db.NoteEntity

class NoteEditViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        noteRepository.update(note)
    }
}