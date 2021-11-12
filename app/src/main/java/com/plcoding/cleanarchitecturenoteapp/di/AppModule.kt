package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.feature_notes.data.data_source.NoteDataBase
import com.plcoding.cleanarchitecturenoteapp.feature_notes.data.repository.NoteRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app :Application) :NoteDataBase{
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepository (db : NoteDataBase) : NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCases(repository: NoteRepository): NoteUseCasesWrapper{
        return NoteUseCasesWrapper(
            getNotesUseCase = GetNotesUseCase(repository = repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository = repository),
            addNoteUseCase = AddNoteUseCase(repository = repository),
            getSingleNoteUseCase = GetSingleNoteUseCase(repository = repository),
            searchUseCase = SearchUseCase()
        )
    }
}