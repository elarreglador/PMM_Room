package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BookViewScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(), // Ocupa todo el espacio disponible
        contentAlignment = Alignment.Center // Alinea al centro horizontal y vertical
    ) {
        Text(
            text = "BookViewScreen"
        )
    }
}