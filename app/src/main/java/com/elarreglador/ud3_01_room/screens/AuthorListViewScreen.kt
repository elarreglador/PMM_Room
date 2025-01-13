package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import com.elarreglador.ud3_01_room.database.Author
import com.elarreglador.ud3_01_room.database.MyDatabase


@Composable
fun AuthorListViewScreen(navController: NavController) {

    // Declarar la base de datos antes de los LaunchedEffect
    val context = LocalContext.current
    val db = remember { MyDatabase.getDatabase(context) }
    // Estado para almacenar la lista de autores
    val authorsState = remember { mutableStateOf<List<Author>>(emptyList()) }
    // Estado para almacenar la consulta de búsqueda
    val searchQuery = remember { mutableStateOf("") }

    // Recuperar todos los autores al cargar la pantalla
    LaunchedEffect(Unit) {
        authorsState.value = db.authorDao().getAllAuthors()
    }

    // Actualizar la lista en función de la búsqueda
    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isEmpty()) {
            // Si el campo de búsqueda está vacío, mostrar todos los autores
            authorsState.value = db.authorDao().getAllAuthors().sortedBy { it.name }
        } else {
            // Filtrar por nombre y apellido
            authorsState.value = (db.authorDao().getAuthorsByName("%${searchQuery.value}%") +
                    db.authorDao().getAuthorsBySurname("%${searchQuery.value}%"))
                        .distinctBy { it.id }
                        .sortedBy { it.name }
        }
    }

    Scaffold(
        topBar = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary) // Fondo de color
                    .padding(start = 0.dp, top = 25.dp, end = 0.dp), // Nos separa de la topBar

            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    IconButton(
                        onClick = { navController.navigate("HomeScreen") },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }

                    Text(
                        text = "Lista de autores",
                        modifier = Modifier.padding(16.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.autor),
                        contentDescription = "Imagen de lista de autores"
                    )
                }

                // Barra de búsqueda
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Buscar autor...") },
                    singleLine = true,
                    maxLines = 1
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("AuthorAddScreen") },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,  // Sombra más pronunciada
                        shape = MaterialTheme.shapes.small,
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir",
                )
            }
        },
        bottomBar = {},
        content = { paddingValues ->
            // Contenido principal, respetando los paddings del Scaffold
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (authorsState.value.isEmpty()) {
                    Text("No hay autores disponibles.")
                } else {
                    Text("${authorsState.value.size} autores listados")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn {
                        items(authorsState.value.size) { index ->
                            val author = authorsState.value[index]

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                Column ( // espacio para el nombre y el autor
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(6.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(
                                        text = "${author.name} ${author.surname}",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )

//
                                    Row() {
                                        Box(
                                            modifier = Modifier
                                                .background(MaterialTheme.colorScheme.tertiary)
                                                .widthIn(min = 60.dp)
                                                .fillMaxHeight()
                                        ) {
                                            Text(
                                                text = "ID: ${author.id}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSecondary,
                                            )
                                        }

                                        Spacer(modifier = Modifier.weight(1f))

                                        Button(
                                            onClick = {
                                                navController.navigate("AuthorEditScreen/${author.id}")
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.secondary,
                                                contentColor = MaterialTheme.colorScheme.onSecondary
                                            ),
                                            shape = MaterialTheme.shapes.small,
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar",
                                                modifier = Modifier
                                                    .size(20.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Button(
                                            onClick = {
                                                navController.navigate("AuthorViewScreen/${author.id}")
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.secondary,
                                                contentColor = MaterialTheme.colorScheme.onSecondary
                                            ),
                                            shape = MaterialTheme.shapes.small,
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = "info.",
                                                modifier = Modifier
                                                    .size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer( modifier = Modifier.height(8.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}


