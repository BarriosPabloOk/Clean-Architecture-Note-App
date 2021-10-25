package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            DefaultRadioButton(
                text = "Título",
                selected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onChange(
                        NoteOrder.Title(noteOrder.orderType)
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onChange(
                        NoteOrder.Color(noteOrder.orderType)
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Fecha",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onChange(
                        NoteOrder.Date(noteOrder.orderType)
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Favoritos",
                selected = noteOrder is NoteOrder.Favorite,
                onSelect = {
                    onChange(
                        NoteOrder.Favorite(noteOrder.orderType)
                    )
                }
            )


        }
///////
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultRadioButton(
                text = "Ascendente",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onChange(
                        noteOrder.copy(OrderType.Ascending)
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Desendente",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onChange(
                        noteOrder.copy(OrderType.Descending)
                    )
                }
            )


        }
    }

}