package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components.NoteItem
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components.OrderSection
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.util.Screen
import kotlinx.coroutines.launch

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
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tus notas!!!!",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteEvents.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort Notes")
                }

            }
            AddEditTextFields(
                text = searchState.value.text,
                hint = searchState.value.hint,
                onValueChange = {

                    viewModel.onEvent(NoteEvents.EnteredSearch(it))
                    viewModel.onEvent(NoteEvents.Search(it, state.value.order))
                },
                onFocusChange = {
                    viewModel.onEvent(NoteEvents.ChangeFocusSearch(it))
                },
                isHintVisible = searchState.value.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )
            AnimatedVisibility(
                visible = state.value.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.value.order,
                    onChange = {
                        viewModel.onEvent(
                            NoteEvents.Order(it)
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(state.value.noteList) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.defaultMinSize(minHeight = 50.dp)
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