package dev.shrishri1108.notesroomapp.presentationLayer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.shrishri1108.notesroomapp.dataLayer.Note
import dev.shrishri1108.notesroomapp.dataLayer.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private var noteDao: NoteDao
) : ViewModel() {
    private var isSortedByDateAdded = MutableStateFlow(true)
    private var notes = isSortedByDateAdded.flatMapLatest {
        if (it) {
            noteDao.getOrderByDateAddedAndDecended()
        } else {
            noteDao.getOrderByTitle()
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        emptyList()
    )

    var _state = MutableStateFlow(NoteState())
    var state = combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
        state.copy(
            note= notes
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        NoteState()
    )


    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.deleteNote -> {
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }
            }
            is NotesEvent.saveNote -> {
                val note= Note(
                    title = state.value.title.value,
                    disp = state.value.disp.value,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    noteDao.updateNote(note = note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        disp = mutableStateOf("")
                    )
                }
            }
            NotesEvent.sortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value


            }
        }
    }


}
