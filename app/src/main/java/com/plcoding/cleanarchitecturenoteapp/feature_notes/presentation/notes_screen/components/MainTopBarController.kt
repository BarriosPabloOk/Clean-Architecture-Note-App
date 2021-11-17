package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.TextStyle
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteEvents
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteState
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NotesViewModel
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.SearchTexFieldState

@ExperimentalComposeUiApi
@Composable
fun MainTopBarController(
    searchState: State<SearchTexFieldState>,
    onSearchIconClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onToggleClicked: () -> Unit,
    onValueChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    textStyle: TextStyle
) {
    DefaultAppBar(
        onSearchIconClicked = {
            onSearchIconClicked()
        },
        onToggleClicked = {
            onToggleClicked()
        }
    )
    if (searchState.value.isSearchFieldVisible) {
        SearchWidget(
            text = searchState.value.text,
            onValueChange = {
                onValueChange(it)
            },
            onClosedClicked = {
                onCloseClicked()
            },
            onSearchClicked = {
                onSearchClicked()
            },
            textStyle = textStyle
        )
    }
}