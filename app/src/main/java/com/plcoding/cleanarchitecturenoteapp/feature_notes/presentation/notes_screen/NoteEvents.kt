package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import androidx.compose.ui.focus.FocusState
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddNoteEvent

sealed class NoteEvents{
    data class Order(val order: NoteOrder) : NoteEvents()
    data class EnteredSearch(val value : String) : NoteEvents()
    data class ChangeFocusSearch(val focusState : FocusState): NoteEvents()
    class Search: NoteEvents()
    data class DeleteNote (val note : Note) : NoteEvents()
    object  RestoreNote : NoteEvents()
    object ToggleOrderSection: NoteEvents()

}
