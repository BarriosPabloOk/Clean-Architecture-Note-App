package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder

sealed class NoteEvents{
    data class Order(val order: NoteOrder) : NoteEvents()
    data class DeleteNote (val note : Note) : NoteEvents()
    object  RestoreNote : NoteEvents()
    object ToggleOrderSection: NoteEvents()

}
