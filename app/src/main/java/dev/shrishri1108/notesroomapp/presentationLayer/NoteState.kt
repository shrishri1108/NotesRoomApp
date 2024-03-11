package dev.shrishri1108.notesroomapp.presentationLayer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.shrishri1108.notesroomapp.dataLayer.Note

class NoteState (
    val note: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val disp: MutableState<String> = mutableStateOf("")
) {
    fun copy(note: List<Note>) = NoteState(note)
    fun copy(title: MutableState<String> ,disp: MutableState<String>) = NoteState(title= title, disp = disp)

}