package dev.shrishri1108.notesroomapp.presentationLayer

import dev.shrishri1108.notesroomapp.dataLayer.Note

sealed interface NotesEvent {
    object sortNotes: NotesEvent
    data class deleteNote(var note: Note): NotesEvent
    data class saveNote(var title: String,
        var disp: String): NotesEvent
}