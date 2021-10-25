package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cleanarchitecturenoteapp.ui.theme.*
import java.lang.Exception
import java.sql.Timestamp

@Entity
data class Note(
    val title : String,
    val content : String,
    val timestamp: Long,
    val color : Int,
    val isFavorite : Boolean = false,
    @PrimaryKey
    val id: Int? = null
){
    companion object{
        val noteColor = listOf(RedOrange, LightGreen, RedPink, BabyBlue, Violet)
    }
}

class InvalidNoteException(message : String) : Exception(message)
