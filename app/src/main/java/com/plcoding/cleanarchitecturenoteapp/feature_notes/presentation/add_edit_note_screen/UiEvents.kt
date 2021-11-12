package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen

sealed class UiEvents(){
    data class ShowSnackBar(val message : String) : UiEvents()
    object SaveNote:UiEvents()
    //data class ShowAlertDialog(val message : String) : UiEvents()
}
