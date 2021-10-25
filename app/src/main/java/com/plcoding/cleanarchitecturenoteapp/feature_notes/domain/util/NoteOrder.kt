package com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util

sealed class NoteOrder(val orderType:OrderType){
    class Title (orderType:OrderType) : NoteOrder(orderType)
    class Color (orderType:OrderType) : NoteOrder(orderType)
    class Date (orderType:OrderType) : NoteOrder(orderType)
    class Favorite(orderType: OrderType) : NoteOrder(orderType)

    fun copy(newOrder: OrderType) : NoteOrder{
        return when(this){
            is Title -> Title(newOrder)
            is Date -> Date(newOrder)
            is Color -> Color(newOrder)
            is Favorite -> Favorite(newOrder)
        }
    }
}
