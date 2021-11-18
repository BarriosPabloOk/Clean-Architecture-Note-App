package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components.*
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val searchState = viewModel.searchState
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {

            MainTopBarController(
                searchState = searchState,
                isSearchFieldVisible = searchState.value.isSearchFieldVisible,
                onSearchIconClicked = {
                    viewModel.onEvent(NoteEvents.isVisibleSearchWidget(!searchState.value.isSearchFieldVisible))
                },
                onSearchClicked = {
                    viewModel.onEvent(
                        NoteEvents.Search(
                            string = searchState.value.text,
                            order = state.value.order
                        )
                    )
                },
                onToggleClicked = {
                    viewModel.onEvent(NoteEvents.ToggleOrderSection)
                },
                onValueChange = {
                    viewModel.onEvent(NoteEvents.EnteredSearch(it))
                    viewModel.onEvent(
                        NoteEvents.Search(
                            string = searchState.value.text,
                            order = state.value.order
                        )
                    )
                },
                onCloseClicked = {
                    viewModel.onEvent(NoteEvents.isVisibleSearchWidget(!searchState.value.isSearchFieldVisible))
                },
                textStyle = MaterialTheme.typography.h5
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")

            }
        },
        scaffoldState = scaffoldState

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = state.value.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    noteOrder = state.value.order,
                    onChange = {
                        viewModel.onEvent(
                            NoteEvents.Order(it)
                        )
                    }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.value.noteList) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditScreen.route
                                            + "?noteId=${note.id}&noteColor=${note.color}&isFavorite=${note.isFavorite}"
                                )
                            },
                        onDeletedClicked = {
                            viewModel.onEvent(NoteEvents.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Nota eliminada",
                                    actionLabel = "DESHACER",
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteEvents.RestoreNote)
                                }
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

    }

}

