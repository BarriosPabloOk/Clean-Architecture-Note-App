package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.repository.NoteRepository

class GetSingleNoteUseCase(
    val repository: NoteRepository
) {
    suspend operator fun invoke(id :Int): Note?{
        return repository.getNoteById(id)
    }
}