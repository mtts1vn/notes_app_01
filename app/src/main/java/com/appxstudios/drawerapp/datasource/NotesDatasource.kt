package com.appxstudios.drawerapp.datasource

import com.appxstudios.drawerapp.model.Note

object NotesDatasource {
    private val notesList: MutableList<Note> = mutableListOf()

    fun getNotes(): List<Note> {
        return notesList
    }

    fun setNotes(notes: List<Note>) {
        notesList.clear()
        notesList.addAll(notes)
    }

    fun addNote(note: Note) {
        notesList.add(note)
    }

    fun removeNote(note: Note) {
        notesList.remove(note)
    }

    fun clearNotes() {
        notesList.clear()
    }
}
