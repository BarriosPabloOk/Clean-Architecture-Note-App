package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components

import android.app.Dialog
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AddEditAlertDialog(
    showDialog:Boolean,
    onConfirm:() ->Unit,
    onDismiss :() ->Unit,
    onDismissRequest: () ->Unit,
) {
    if (showDialog){
        AlertDialog(
            title ={Text(text = "¿Desea guardar los cambios hechos en la nota?")},
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



