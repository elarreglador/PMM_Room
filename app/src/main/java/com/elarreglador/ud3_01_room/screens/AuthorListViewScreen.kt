package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import com.elarreglador.ud3_01_room.database.Author
import com.elarreglador.ud3_01_room.database.MyDatabase



@Composable
fun AuthorListViewScreen(navController: NavController) {

    // Estado para almacenar la lista de autores
    val authorsState = remember { mutableStateOf<List<Author>>(emptyList()) }
    // Recuperar los autores al iniciar la pantalla
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        // Mover la obtención de la base de datos fuera del cuerpo composable
        val db = MyDatabase.getDatabase(context)
        val authors = db.authorDao().getAllAuthors()
        authorsState.value = authors
    }

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
                    text = "Lista de autores",
                    modifier = Modifier.padding(16.dp),
                )

                Image(
                    painter = painterResource(id = R.drawable.autor),
                    contentDescription = "Imagen de lista de autores"
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
        bottomBar = {
        },
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
                    LazyColumn {
                        items(authorsState.value.size) { index ->

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                Box( // box para la id del autor
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.secondary)
                                        .widthIn(min = 60.dp) // Establece un ancho mínimo
                                        .fillMaxHeight()
                                ) {
                                    val author = authorsState.value[index]
                                    Text(
                                        text = "${author.id}",
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                        .padding(6.dp)
                                        .fillMaxHeight()
                                ) {
                                    val author = authorsState.value[index]
                                    Text(
                                        text = "${author.name} ${author.surname} \n${author.country}",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}


