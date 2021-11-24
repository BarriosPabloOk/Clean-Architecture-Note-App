package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_notes.data.preferences.PreferenceManager
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases.NoteUseCasesWrapper
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCasesWrapper: NoteUseCasesWrapper,
    private val preferenceManager: PreferenceManager,

): ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state : State<NoteState> = _state


    private val _searchState = mutableStateOf<SearchTexFieldState>(
        SearchTexFieldState(
            text = "",
            hint = "Buscar nota...",
            isSearchFieldVisible = false
        )
    )
    val searchState : State<SearchTexFieldState> = _searchState

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob : Job? =null




    init{
        viewModelScope.launch {
            val a = preferenceManager.readDataStore.collectLatest {
                if (it == "Descending"){
                    getNotes(string = "", noteOrder = NoteOrder.Date(OrderType.Descending))
                }else{
                    getNotes(string = "", noteOrder = NoteOrder.Date(OrderType.Ascending))
                }

            }
        }

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
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(string: String, noteOrder : NoteOrder){
        getNotesJob?.cancel()
        if (_searchState.value.text.isEmpty()){
            getNotesJob = noteUseCasesWrapper.getNotesUseCase(noteOrder)
                .onEach { notes ->
                    _state.value = state.value.copy(
                        noteList = notes,
                        order = noteOrder,
                    )
                }.launchIn(viewModelScope)
        }else{
            getNotesJob = noteUseCasesWrapper.searchUseCase(string = string, noteOrder = noteOrder)
                .onEach { notes ->
                    _state.value = state.value.copy(
                        noteList = notes,
                        order = noteOrder,
                    )
                }.launchIn(viewModelScope)
        }


    }

    fun saveInDataStore(//noteOrder: String,
                        orderType: String) = viewModelScope.launch{
        preferenceManager.saveInDataStore(orderType = orderType)
    }

}