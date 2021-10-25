package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.util

sealed class Screen(val route :String){
    object NotesScreen: Screen("notes_screen")
    object AddEditScreen: Screen("add_edit_screen")
    object InitialScreen : Screen("splash_screen")
}
