package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@ExperimentalComposeUiApi
@Composable
fun SearchWidget(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onClosedClicked : () -> Unit,
    onSearchClicked : (String) -> Unit,
    textStyle: TextStyle,

) {
    val keyboardController = LocalSoftwareKeyboardController.current


    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
            TextField(
                value = text,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = textStyle,

                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Buscar nota...",
                        style= textStyle,
                        color = MaterialTheme.colors.onPrimary,
                    )
                },
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if(text.isNotEmpty()){
                                onValueChange("")
                            }else{
                                onClosedClicked()
                            }

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onPrimary.copy(
                        alpha = ContentAlpha.medium
                    )
                )



            )

        }
    }

@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchWidgetPreview() {
    SearchWidget(
        text = "",
        onValueChange = {},
        onClosedClicked = { /*TODO*/ },
        onSearchClicked ={} ,
        textStyle = MaterialTheme.typography.h6
    )
}
