package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun OptionMenu(
    expanded : Boolean,
    onDismissRequest:()->Unit,
    darkMode : MutableState<Boolean>,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {onDismissRequest()}
    ) {
        DropdownMenuItem(
            onClick = {
                darkMode.value = !darkMode.value
            }) {
            Text(text = if (darkMode.value)"Modo Claro" else "Modo Oscuro")
        }
        DropdownMenuItem(
            onClick = {}) {
            Text(text = "Crear copia de seguridad")
        }
        DropdownMenuItem(
            onClick = {}) {
            Text(text = "Restaurar copia de seguridad")
        }
    }
    
}