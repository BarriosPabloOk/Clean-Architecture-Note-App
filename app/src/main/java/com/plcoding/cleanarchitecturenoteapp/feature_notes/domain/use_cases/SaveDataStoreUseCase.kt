package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_notes.data.preferences.PreferenceManager
import javax.inject.Inject


class SaveDataStoreUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager
) {

    suspend operator fun invoke(noteOrder: String, orderType: String){
        return preferenceManager.saveInDataStore(noteOrder = noteOrder, orderType = orderType)
    }
}