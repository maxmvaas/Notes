package ru.maxmv.notes.data

import ru.maxmv.notes.data.db.NoteEntity

fun noteToEntity(note: Note) = NoteEntity(0, title = note.title, text = note.text)

fun entityToNote(noteEntity: NoteEntity) =
    Note(id = noteEntity.id, title = noteEntity.title, text = noteEntity.text, 0)