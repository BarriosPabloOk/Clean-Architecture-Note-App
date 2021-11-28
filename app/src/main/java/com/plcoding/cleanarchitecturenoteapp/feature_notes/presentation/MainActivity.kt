package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.plcoding.cleanarchitecturenoteapp.databinding.ActivityMainBinding
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddEditViewModel

import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.navigation.Navigation
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            //val darkTheme = remember{ mutableStateOf(false)}
            CleanArchitectureNoteAppTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Navigation(
                        navController = rememberNavController(),
                        //darkMode = darkTheme
                        )
                }
            }
        }

    }
}
