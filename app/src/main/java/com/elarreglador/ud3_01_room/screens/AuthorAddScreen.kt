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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R
import com.elarreglador.ud3_01_room.database.Author
import androidx.compose.ui.platform.LocalContext
import com.elarreglador.ud3_01_room.database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AuthorAddScreen(navController: NavController) {

    val authorViewModel = null
    var context = LocalContext.current
    var miBD = MyDatabase.getDatabase(context)
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }

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
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val dbName = "myBD"
                val result = context.deleteDatabase(dbName)
                if (result) {
                    println("Base de datos borrada exitosamente.")
                } else {
                    println("No se pudo borrar la base de datos o no existe.")
                }
            }) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar Base de Datos"
                    )
                    Text(text = "Borrar BD")
                }
            }
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
                    value = name.value,
                    onValueChange = { newText -> name.value = newText },
                    label = { Text("Nombre") },
                    maxLines = 1, // Limita a una línea
                    singleLine = true, // Garantiza que sea un campo de una sola línea
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer (modifier = Modifier.height(10.dp))

                TextField(
                    value = surname.value,
                    onValueChange = { newText -> surname.value = newText },
                    label = { Text("Apellido") },
                    maxLines = 1, // Limita a una línea
                    singleLine = true, // Garantiza que sea un campo de una sola línea
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer (modifier = Modifier.height(10.dp))

                TextField(
                    value = country.value,
                    onValueChange = { newText -> country.value = newText },
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
                        // Ejecutar la operación de base de datos en un hilo en segundo plano
                        CoroutineScope(Dispatchers.IO).launch {
                            val author = Author(
                                name = name.value,
                                surname = surname.value,
                                country = country.value
                            )
                            miBD.authorDao().insertAuthor(author) // Inserta el autor
                        }
                        navController.navigate("AuthorListViewScreen")
                    }) {
                        Text("Agregar autor")
                    }
                }

            }
        }
    )
}