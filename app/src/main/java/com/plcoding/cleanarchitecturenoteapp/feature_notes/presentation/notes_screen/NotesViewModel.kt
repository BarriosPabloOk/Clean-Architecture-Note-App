package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases.NoteUseCasesWrapper
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCasesWrapper: NoteUseCasesWrapper,

): ViewModel() {

    private val _noteState = mutableStateOf(NoteState())
    val noteState : State<NoteState> = _noteState


    private val _searchState = mutableStateOf<SearchTexFieldState>(
        SearchTexFieldState(
            text = "",
            hint = "Buscar nota...",
            isSearchFieldVisible = false
        )
    )
    val searchState : State<SearchTexFieldState> = _searchState


    private val _dropDownMenuState = mutableStateOf<Boolean>(false)
    val dropDownMenuState : State<Boolean> = _dropDownMenuState

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob : Job? =null




    init{
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCasesWrapper.readDataStoreUseCase{
                getNotes("", it)
            }
        }

    }

    fun onEvent(events: NoteEvents){
        when(events){
            is NoteEvents.Order -> {
                if (noteState.value.order::class == events.order::class &&
                        noteState.value.order.orderType == events.order.orderType){
                    return
                }
                getNotes(_searchState.value.text,events.order)
            }


            is NoteEvents.EnteredSearch->{
                _searchState.value = searchState.value.copy(
                    text = events.value
                )
            }
            is NoteEvents.isVisibleSearchWidget ->{
                _searchState.value = searchState.value.copy(
                    isSearchFieldVisible = events.value
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
                _noteState.value = noteState.value.copy(
                    isOrderSectionVisible = !noteState.value.isOrderSectionVisible
                )
            }
            is NoteEvents.DropDownMenu->{
                _dropDownMenuState.value = !dropDownMenuState.value
            }
        }
    }

    private fun getNotes(string: String, noteOrder : NoteOrder){
        getNotesJob?.cancel()
        if (_searchState.value.text.isEmpty()){
            getNotesJob = noteUseCasesWrapper.getNotesUseCase(noteOrder)
                .onEach { notes ->
                    _noteState.value = noteState.value.copy(
                        noteList = notes,
                        order = noteOrder,
                    )
                }.launchIn(viewModelScope)
        }else{
            getNotesJob = noteUseCasesWrapper.searchUseCase(string = string, noteOrder = noteOrder)
                .onEach { notes ->
                    _noteState.value = noteState.value.copy(
                        noteList = notes,
                        order = noteOrder,
                    )
                }.launchIn(viewModelScope)
        }


    }

    fun saveInDataStore(noteOrder: String, orderType: String) = viewModelScope.launch{
        noteUseCasesWrapper.saveDataStoreUseCase(noteOrder = noteOrder, orderType = orderType)
    }

}