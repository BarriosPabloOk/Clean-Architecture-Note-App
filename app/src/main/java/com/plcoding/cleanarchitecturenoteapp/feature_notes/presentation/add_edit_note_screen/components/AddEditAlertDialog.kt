package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components

import android.app.Dialog
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle

@Composable
fun AddEditAlertDialog(
    showDialog:Boolean,
    onConfirm:() ->Unit,
    onDismiss :() ->Unit,
    onDismissRequest: () ->Unit,
) {
    if (showDialog){
        AlertDialog(
            title ={Text(
                text = "¿Desea guardar los cambios hechos en la nota?",
            fontStyle = MaterialTheme.typography.body1.fontStyle
            )},
            text = {
                Text(
                    text = "El título y contenido de la nota deben tener texto",
                    fontStyle = MaterialTheme.typography.body2.fontStyle)},
            onDismissRequest = onDismissRequest,
            confirmButton = {
                Button(onClick =  onConfirm) {
                    Text(text = "Guardar")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(text = "Descartar")
                }
            }
        )
    }

}



