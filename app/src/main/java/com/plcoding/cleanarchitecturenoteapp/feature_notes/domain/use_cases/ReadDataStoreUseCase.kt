package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_notes.data.preferences.PreferenceManager
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.DataStoreNoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.OrderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ReadDataStoreUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager,
) {

    suspend operator fun invoke(getNotes:(NoteOrder)->Unit)  {
        withContext(Dispatchers.IO) {
            preferenceManager.readDataStore.collectLatest {
                when (it.OrderType) {
                    DataStoreNoteOrder.ASCENDING.name -> {
                        when (it.NoteOrder) {
                            DataStoreNoteOrder.TITLE.name -> {
                                getNotes(NoteOrder.Title(OrderType.Ascending))
                            }
                            DataStoreNoteOrder.COLOR.name -> {
                                getNotes(NoteOrder.Color(OrderType.Ascending))
                            }
                            DataStoreNoteOrder.DATE.name -> {
                                getNotes(NoteOrder.Date(OrderType.Ascending))
                            }
                            DataStoreNoteOrder.FAVORITE.name -> {
                                getNotes(NoteOrder.Favorite(OrderType.Ascending))
                            }
                        }
                    }
                    DataStoreNoteOrder.DESCENDING.name -> {
                        when (it.NoteOrder) {
                            DataStoreNoteOrder.TITLE.name -> {
                                getNotes(NoteOrder.Title(OrderType.Descending))
                            }
                            DataStoreNoteOrder.COLOR.name -> {
                                getNotes(NoteOrder.Color(OrderType.Descending))
                            }
                            DataStoreNoteOrder.DATE.name -> {
                                getNotes(NoteOrder.Date(OrderType.Descending))
                            }
                            DataStoreNoteOrder.FAVORITE.name -> {
                                getNotes(NoteOrder.Favorite(OrderType.Descending))
                            }
                        }
                    }

                }

            }
        }

    }
}