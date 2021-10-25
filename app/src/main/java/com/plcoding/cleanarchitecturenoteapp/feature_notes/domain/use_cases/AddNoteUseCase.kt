package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository,
){

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note cannot be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note cannot be empty")
        }
        return repository.insertNote(note)
    }
}