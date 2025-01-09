package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R

@Composable
fun AuthorAddScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary) // Fondo de color
                    .padding(25.dp), // Nos separa de la topBar

                verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
            ) {

                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons. AutoMirrored. Filled. ArrowBack,
                        contentDescription = "Volver",
                    )
                }

                Text(
                    text = "Nuevo autor",
                    modifier = Modifier.padding(16.dp),
                )
            }
        },
        bottomBar = {
        },
        content = { paddingValues ->
            // Contenido principal, respetando los paddings del Scaffold
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues) // Aplica el padding del Scaffold
                    .padding(16.dp) // Agrega un padding adicional si es necesario
            ) {

                Image(
                    painter = painterResource(id = R.drawable.autor),
                    contentDescription = "Imagen de libro"
                )

                Spacer (modifier = Modifier.height(10.dp))

                TextField(
                    value = name,
                    onValueChange = { newText -> name = newText },
                    label = { Text("Nombre") },
                    maxLines = 1, // Limita a una línea
                    singleLine = true, // Garantiza que sea un campo de una sola línea
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer (modifier = Modifier.height(10.dp))

                TextField(
                    value = surname,
                    onValueChange = { newText -> surname = newText },
                    label = { Text("Apellido") },
                    maxLines = 1, // Limita a una línea
                    singleLine = true, // Garantiza que sea un campo de una sola línea
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer (modifier = Modifier.height(10.dp))

                TextField(
                    value = country,
                    onValueChange = { newText -> country = newText },
                    label = { Text("Pais") },
                    maxLines = 1, // Limita a una línea
                    singleLine = true, // Garantiza que sea un campo de una sola línea
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer (modifier = Modifier.height(10.dp))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        navController.navigate("BookListViewScreen")
                    }) {
                        Text("Agregar autor")
                    }
                }

            }
        }
    )
}