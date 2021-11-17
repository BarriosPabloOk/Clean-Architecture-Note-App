package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.ui.theme.GreenApp

@Composable
fun DefaultAppBar(
    onSearchIconClicked: () -> Unit,
    onToggleClicked: ()-> Unit
) {
    TopAppBar(
        modifier = Modifier.height(70.dp),
        title = {
            Text(
                text = "Tus Notas!!!",
                fontSize = MaterialTheme.typography.h4.fontSize
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

        },
        backgroundColor = GreenApp
    )
}

@Preview
@Composable
fun DefaultAppBArrPreview() {
    DefaultAppBar(
        onSearchIconClicked = {},
        onToggleClicked = {}
    )
}