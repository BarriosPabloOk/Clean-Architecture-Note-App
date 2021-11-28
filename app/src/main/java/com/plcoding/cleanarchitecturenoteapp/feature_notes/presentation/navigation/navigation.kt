package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddEditViewModel
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.NotesScreen
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.splash_screen.SplashScreen
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.util.Screen


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController,

) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable(Screen.NotesScreen.route){
            NotesScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditScreen.route + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(name = "noteId"){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "noteColor"){
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ){
            val color = it.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(
                navController = navController,
                noteColor = color,
            )
        }
        composable(Screen.InitialScreen.route){
            SplashScreen(navController = navController)
        }
    }

}