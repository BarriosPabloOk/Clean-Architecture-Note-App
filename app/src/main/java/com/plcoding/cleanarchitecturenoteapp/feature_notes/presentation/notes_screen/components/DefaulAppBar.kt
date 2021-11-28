package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NoteEvents
import com.plcoding.cleanarchitecturenoteapp.ui.theme.GreenApp

@Composable
fun DefaultAppBar(
    onSearchIconClicked: () -> Unit,
    onToggleClicked: ()-> Unit,
    onOptionClicked: () ->Unit,
    optionMenuState :Boolean,
    darkMode : MutableState<Boolean>,
    optionDismiss:()->Unit
) {


    TopAppBar(
        modifier = Modifier.height(70.dp),
        title = {
            Text(
                text = "Tus Notas!!!",
                fontSize = MaterialTheme.typography.h5.fontSize
            )
        },
        actions = {
            IconButton(onClick = {onSearchIconClicked()}
            ) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "SearchIcon",
                    tint= MaterialTheme.colors.onPrimary

                )
            }
            IconButton(onClick = {onToggleClicked()}
            ) {
                Icon(imageVector = Icons.Default.Sort,
                    contentDescription = "SearchIcon",
                    tint= MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = {onOptionClicked()}
            ) {
                Icon(imageVector = Icons.Default.MoreVert,
                    contentDescription = "SearchIcon",
                    tint= MaterialTheme.colors.onPrimary
                )
            }
            OptionMenu(
                expanded = optionMenuState,
                onDismissRequest = {optionDismiss()},
                darkMode = darkMode
            )

        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

