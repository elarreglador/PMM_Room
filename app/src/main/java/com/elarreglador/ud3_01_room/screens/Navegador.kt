package com.elarreglador.ud3_01_room.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

// Funcion que permite la navegacion entre pantallas
@Composable
fun Navegador() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "principal") {
        // (route es el apodo para llamar a la ventana) {funcion/pantalla + parametro a usar}

        composable( // ventana principal o home
            route = "principal"
        ) {
            HomeScreen(navController)
        }

        composable(
            route = "AuthorListViewScreen"
        ){
            AuthorListViewScreen(navController)
        }

        composable(
            route = "AuthorAddScreen"
        ){
            AuthorAddScreen(navController)
        }

        composable(
            route = "BookListViewScreen"
        ){
            BookListViewScreen(navController)
        }

        composable(
            route = "BookAddScreen"
        ){
            BookAddScreen(navController)
        }

    }
}