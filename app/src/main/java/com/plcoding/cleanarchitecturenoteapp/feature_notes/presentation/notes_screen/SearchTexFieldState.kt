package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

data class SearchTexFieldState(
    val text: String = "",
    val hint: String = "",
    val isSearchFieldVisible: Boolean,
)
