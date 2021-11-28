package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components.AddEditTextFields
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components.AddEditAlertDialog
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components.AddEditTopBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel(),
    noteColor: Int,
) {

    val context = LocalContext.current
    val noteTitle = viewModel.noteTitle
    val noteContent = viewModel.noteContent
    val isFavoriteState = viewModel.addToFAvoritesState.value
    var alertState = viewModel.alertDialogState.value
    val scaffoldState = rememberScaffoldState()
    val backgroundAnimatable = remember {
        Animatable(
            initialValue = Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }
    val scope = rememberCoroutineScope()
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

//    DisposableEffect(key1 = viewModel) {
//        onDispose { viewModel.onEvent(AddNoteEvent.SaveNoteOnStop) }
//    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { events ->
            when (events) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = events.message
                    )
                }
                is UiEvents.SaveNote -> {
                    navController.navigateUp()
                }

            }
        }
    }

    Scaffold(
        topBar = {
            AddEditTopBar(
                favoriteState = isFavoriteState,
                onFavoriteClicked = {viewModel.onEvent(AddNoteEvent.AddToFavorites(!isFavoriteState))},
                onBackPressed = {viewModel.onEvent(AddNoteEvent.BackPressed(!alertState))}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.navigationBarsWithImePadding()
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")

            }
        },
        scaffoldState = scaffoldState,
        modifier = Modifier.navigationBarsWithImePadding()

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColor.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.colorState.value == colorInt)
                                    Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    backgroundAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddNoteEvent.ChangeColor(colorInt))
                            }

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                AddEditTextFields(
                    text = noteTitle.value.text,
                    hint = noteTitle.value.hint,
                    onValueChange = {
                        viewModel.onEvent(AddNoteEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddNoteEvent.ChangeFocusTitle(it))
                    },
                    isHintVisible = noteTitle.value.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() }
                    ),
                    modifier = Modifier.focusRequester(focusRequester)
                )

            }


            Spacer(modifier = Modifier.height(16.dp))
            AddEditTextFields(
                text = noteContent.value.text,
                hint = noteContent.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddNoteEvent.ChangeFocusContent(it))
                },
                isHintVisible = noteContent.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .onKeyEvent {
                        if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
                            viewModel.onEvent(AddNoteEvent.BackPressed(!alertState))

                        }
                        true
                    }
                    .focusRequester(focusRequester)
            )
        }
    }
    AddEditAlertDialog(
        showDialog = alertState,
        onConfirm = { viewModel.onEvent(AddNoteEvent.SaveNote) },
        onDismiss = { navController.navigateUp() },
        onDismissRequest = { viewModel.onEvent(AddNoteEvent.BackPressed(!alertState)) }
    )
}



