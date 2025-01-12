package com.elarreglador.ud3_01_room.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

// Funcion que permite la navegacion entre pantallas
@Composable
fun Navegador() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        // (route es el apodo para llamar a la ventana) {funcion/pantalla + parametro a usar}

        composable( // ventana principal o home
            route = "HomeScreen"
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
            route = "AuthorEditScreen/{authorId}"  // Incluir el parámetro authorId en la ruta
        ) { backStackEntry ->
            // Obtener el valor de authorId de la ruta
            val authorId = backStackEntry.arguments?.getString("authorId")?.toInt() ?: 0
            AuthorEditScreen(navController, authorId)
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

        composable(
            route = "BookEditScreen/{bookId}"  // Incluir el parámetro authorId en la ruta
        ) { backStackEntry ->
            // Obtener el valor de authorId de la ruta
            val bookId = backStackEntry.arguments?.getString("bookId")?.toInt() ?: 0
            AuthorEditScreen(navController, bookId)
        }
    }
}