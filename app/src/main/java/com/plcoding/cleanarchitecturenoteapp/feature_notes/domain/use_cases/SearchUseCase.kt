package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchUseCase @Inject constructor(
    //private val repository:
    private val getNotesUseCase: GetNotesUseCase
) {

    operator fun invoke(string: String, noteOrder: NoteOrder) : Flow<List<Note>> {
        val noteList = getNotesUseCase(noteOrder = noteOrder).map { notes->
                notes.filter { singleNote->
                    singleNote.title.lowercase().contains(string.lowercase())
                }
            }
        return noteList
    }
}