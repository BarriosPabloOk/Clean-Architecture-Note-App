package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

data class NoteUseCasesWrapper (
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getSingleNoteUseCase: GetSingleNoteUseCase,
    val searchUseCase: SearchUseCase,
)