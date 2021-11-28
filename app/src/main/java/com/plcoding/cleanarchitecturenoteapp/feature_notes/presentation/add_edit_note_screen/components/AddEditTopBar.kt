package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.TextNoteFieldState
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components.OptionMenu

@Composable
fun AddEditTopBar(
    favoriteState: Boolean,
    onFavoriteClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.height(70.dp),
        title = {
            Text(
                text = "Editar nota",
                fontSize = MaterialTheme.typography.h5.fontSize
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "FavoriteIcon",
                    tint = MaterialTheme.colors.onPrimary

                )
            }
        },
        actions = {
            IconButton(onClick = { onFavoriteClicked() }
            ) {
                Icon(
                    imageVector = if (favoriteState) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "FavoriteIcon",
                    tint = MaterialTheme.colors.onPrimary

                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )

}