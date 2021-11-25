package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen

import androidx.compose.ui.focus.FocusState

sealed class AddNoteEvent(){
    data class EnteredTitle(val value : String) : AddNoteEvent()
    data class ChangeFocusTitle(val focusState : FocusState): AddNoteEvent()
    data class EnteredContent (val value : String): AddNoteEvent()
    data class ChangeFocusContent(val focusState : FocusState): AddNoteEvent()
    data class ChangeColor(val color : Int): AddNoteEvent()
    data class AddToFavorites(val IsFavorite : Boolean) : AddNoteEvent()
    data class BackPressed(val pressed: Boolean) : AddNoteEvent()
    object SaveNote: AddNoteEvent()
}


