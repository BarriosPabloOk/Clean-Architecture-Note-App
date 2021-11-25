package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases.NoteUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCasesWrapper: NoteUseCasesWrapper,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _noteTitle = mutableStateOf<TextNoteFieldState>(
        TextNoteFieldState(
            hint = "Escribe un título"
        )
    )
    val noteTitle: State<TextNoteFieldState> = _noteTitle

    private val _noteContent = mutableStateOf<TextNoteFieldState>(
        TextNoteFieldState(
            hint = "Esto está muy vació... Agrega algo de contenido..."
        )
    )
    val noteContent: State<TextNoteFieldState> = _noteContent

    private val _colorState = mutableStateOf<Int>(Note.noteColor.random().toArgb())
    val colorState: State<Int> = _colorState


    private val _addToFAvoritesState = mutableStateOf<Boolean>(false)
    val addToFAvoritesState : State<Boolean> = _addToFAvoritesState


    private val _alertDialogState = mutableStateOf<Boolean>(false)
    val alertDialogState : State<Boolean> = _alertDialogState

    private val _evenFlow = MutableSharedFlow<UiEvents>()
    val eventFlow : SharedFlow<UiEvents> = _evenFlow

    private var currentNoteId :Int? = null

    init{
        savedStateHandle.get<Int>("noteId")?.let { noteId->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCasesWrapper.getSingleNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _colorState.value = note.color
                        _addToFAvoritesState.value = note.isFavorite
                    }
                }
            }
        }
    }

    fun onEvent(events: AddNoteEvent) {
        when (events) {
            is AddNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = events.value
                )
            }
            is AddNoteEvent.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !events.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = events.value
                )
            }
            is AddNoteEvent.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !events.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddNoteEvent.ChangeColor -> {
                _colorState.value = events.color
            }
            is AddNoteEvent.AddToFavorites -> {
                _addToFAvoritesState.value = events.IsFavorite

            }
            is AddNoteEvent.BackPressed ->{
                _alertDialogState.value = events.pressed

            }
            is AddNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCasesWrapper.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = colorState.value,
                                id = currentNoteId,
                                isFavorite = addToFAvoritesState.value
                            )
                        )
                        _evenFlow.emit(UiEvents.SaveNote)
                    }catch (e : InvalidNoteException){
                        _evenFlow.emit(UiEvents.ShowSnackBar(message = e.message ?: "No se pudo guardar la nota"))
                    }
                }

            }

        }
    }

}


