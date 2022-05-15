package ru.maxmv.notes.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import ru.maxmv.notes.data.db.NoteEntity
import ru.maxmv.notes.data.db.dao.NoteDao

class NoteRepository(private val dao: NoteDao) {

    fun getNotes() = dao.getAll()

    suspend fun addNote(note: Note) {
        dao.insert(noteToEntity(note))
    }

    suspend fun deleteNote(id: Int) {
        dao.delete(id)
    }

    fun update(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(note)
        }
    }

}