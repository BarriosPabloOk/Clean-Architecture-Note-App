package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.animation.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteEvents
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteState
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NotesViewModel
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.SearchTexFieldState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MainTopBarController(
    searchState: State<SearchTexFieldState>,
    //optionMenuState : Boolean,
    isSearchFieldVisible : Boolean,
    onSearchIconClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onToggleClicked: () -> Unit,
    //onOptionClicked : () -> Unit,
    //optionDismiss:()->Unit,
    onValueChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    textStyle: TextStyle,
    //darkMode : MutableState<Boolean>,
) {
    DefaultAppBar(
        onSearchIconClicked = {
            onSearchIconClicked()
        },
        onToggleClicked = {
            onToggleClicked()
        },
        //onOptionClicked = {onOptionClicked()},
        //optionMenuState= optionMenuState,
        //optionDismiss = optionDismiss,
        //darkMode = darkMode
    )
    AnimatedVisibility(
        visible = isSearchFieldVisible,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it/2 }),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it/2 })
    ) {
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