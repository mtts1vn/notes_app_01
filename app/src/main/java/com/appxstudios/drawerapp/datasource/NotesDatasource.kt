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

    fun checkIfExist(note: Note?): Boolean {
        return notesList.contains(note);
    }

    fun getIndex(note: Note): Int {
        return notesList.indexOf(note);
    }

    fun replace(note: Note, index: Int) {
        notesList[index] = note;
    }
}
