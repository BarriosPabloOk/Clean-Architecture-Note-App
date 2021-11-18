package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.util.Screen
import kotlinx.coroutines.delay
import com.plcoding.cleanarchitecturenoteapp.R
import com.plcoding.cleanarchitecturenoteapp.ui.theme.DarkGray

@Composable
fun SplashScreen(
    navController: NavController,
    sharedPrefState: Boolean = false,
) {

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(
        key1 = true,
    ){
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Screen.NotesScreen.route){
            popUpTo(navController.graph.startDestinationId){
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .scale(scale.value)
                    .size(128.dp),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = "Splash_Icon"
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Buenas Notas",
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h3.fontSize,
                modifier = Modifier
                    .scale(scale.value),


            )
        }

    }

}