package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases.NoteUseCasesWrapper
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.TextNoteFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCasesWrapper: NoteUseCasesWrapper,
): ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state : State<NoteState> = _state


    private val _searchState = mutableStateOf<TextNoteFieldState>(
        TextNoteFieldState(
            hint = "Buscar nota..."
        )
    )
    val searchState : State<TextNoteFieldState> = _searchState

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob : Job? =null

    init{
        getNotes(string = "", noteOrder = NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(events: NoteEvents){
        when(events){
            is NoteEvents.Order -> {
                if (state.value.order::class == events.order::class &&
                        state.value.order.orderType == events.order.orderType){
                    return
                }
                getNotes(_searchState.value.text,events.order)
            }
            is NoteEvents.EnteredSearch->{
                _searchState.value = searchState.value.copy(
                    text = events.value
                )
            }
            is NoteEvents.ChangeFocusSearch->{
                _searchState.value = searchState.value.copy(
                    isHintVisible = !events.focusState.isFocused &&
                            searchState.value.text.isBlank()
                )
            }
            is NoteEvents.Search->{
                getNotes(_searchState.value.text, events.order)
            }
            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCasesWrapper.deleteNoteUseCase(events.note)
                    recentlyDeletedNote = events.note
                }

            }
            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCasesWrapper.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(string: String, noteOrder : NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCasesWrapper.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    noteList = notes.filter { it.title.lowercase().contains(string.lowercase()) },
                    order = noteOrder,
                )
            }.launchIn(viewModelScope)

    }

}