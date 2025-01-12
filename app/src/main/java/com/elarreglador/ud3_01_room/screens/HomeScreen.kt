package com.elarreglador.ud3_01_room.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R


@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
        },
        bottomBar = {
        },
        content = { paddingValues ->
            // Contenido principal, respetando los paddings del Scaffold
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    painter = painterResource(id = R.drawable.icono),
                    contentDescription = "Room: A library management App"
                )

                Text( "App de gestion de bibliotecas" , modifier = Modifier.padding(paddingValues), textAlign = TextAlign.Justify)

                Button(onClick = {
                    navController.navigate("AuthorListViewScreen")
                }) {
                    Text("Lista de autores")
                }

                Button(onClick = {
                    navController.navigate("BookListViewScreen")
                }) {
                    Text("Lista de libros", )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    // Crear un intent para abrir la URL en el navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/elarreglador/PMM_Room"))
                    context.startActivity(intent)
                }) {
                    Text("+ Info.", )
                }
            }

        }
    )

}