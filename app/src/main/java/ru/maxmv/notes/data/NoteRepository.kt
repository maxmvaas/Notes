package ru.maxmv.notes.data

import ru.maxmv.notes.data.db.dao.NoteDao

class NoteRepository(private val dao: NoteDao) {

    fun getNotes() = dao.getAll()

    suspend fun addNote(note: Note) {
        dao.insert(noteToEntity(note))
    }
}