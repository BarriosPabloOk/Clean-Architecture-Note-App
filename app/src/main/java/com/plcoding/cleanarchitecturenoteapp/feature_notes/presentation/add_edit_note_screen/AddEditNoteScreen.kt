package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen

import android.content.DialogInterface
import android.view.KeyEvent
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components.AddEditTextFields
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel(),
    noteColor: Int,
    isFavorite: Boolean
) {

    //val context = LocalContext.current
    val noteTitle = viewModel.noteTitle
    val noteContent = viewModel.noteContent
    val isFavoriteState = if (!isFavorite) viewModel.addToFAvoritesState.value else isFavorite



//    if (noteTitle.value.text.isNotBlank() && noteContent.value.text.isNotBlank()) {
//        BackHandler(onBack = {
//
//            viewModel.onEvent(AddNoteEvent.BackPressed(!viewModel.alertDialogState.value))
//        })
//    }


    val scaffoldState = rememberScaffoldState()
    val backgroundAnimatable = remember {
        Animatable(
            initialValue = Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }
    val scope = rememberCoroutineScope()

    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

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
//                is UiEvents.ShowAlertDialog -> {
////                    val result = scaffoldState.snackbarHostState.showSnackbar(
////                        message = events.message,
////                        actionLabel = "Guardar"
////                    )
////                    if (result == SnackbarResult.ActionPerformed) {
////                        viewModel.onEvent(AddNoteEvent.SaveNote)
////                    } else {
////                        navController.navigateUp()
////                    }
//                    val alert = android.app.AlertDialog.Builder(context)
//                        .setTitle("Guardar cambios")
//                        .setMessage("¿Desea guardar los cambioso prefiere descartarlos?")
//                        .setNegativeButton("Descartar"){view,_ ->
//                            navController.navigateUp()
//                            view.dismiss()
//                        }
//                        .setPositiveButton("Guardar"){view, _ ->
//                            viewModel.onEvent(AddNoteEvent.SaveNote)
//                            view.dismiss()
//                        }
//                        .setCancelable(false)
//                        .create()
//
//                    alert.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.Black.toArgb())
//                    alert.show()
//                }
            }
        }
    }

    Scaffold(
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() }
                    ),
                    modifier = Modifier.focusRequester(focusRequester)
                )



                Icon(
                    imageVector = if (isFavoriteState) Icons.Default.Star
                    else Icons.Default.StarBorder,
                    contentDescription = "add to favorites",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.onEvent(AddNoteEvent.AddToFavorites(!isFavoriteState))
                        },
                    tint = Color.Black
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
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .focusRequester(focusRequester)
            )
        }
    }
}

fun alertDialog() {

}
