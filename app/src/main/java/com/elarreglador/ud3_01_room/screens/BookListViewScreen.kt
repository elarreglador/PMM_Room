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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R
import com.elarreglador.ud3_01_room.database.Author
import com.elarreglador.ud3_01_room.database.Book
import com.elarreglador.ud3_01_room.database.MyDatabase

@Composable
fun BookListViewScreen(navController: NavController) {

    // Declarar la base de datos antes de los LaunchedEffect
    val context = LocalContext.current
    val db = remember { MyDatabase.getDatabase(context) }
    // Estado para almacenar la lista de libros
    val bookState = remember { mutableStateOf<List<Book>>(emptyList()) }
    // Estado para almacenar la consulta de búsqueda
    val searchQuery = remember { mutableStateOf("") }

    // Recuperar todos los autores al cargar la pantalla
    LaunchedEffect(Unit) {
        bookState.value = db.bookDao().getAllBooks()
    }

    // Actualizar la lista en función de la búsqueda
    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isEmpty()) {
            // Si el campo de búsqueda está vacío, mostrar todos los libros
            bookState.value = db.bookDao().getAllBooks()
        } else {
            // Filtrar por nombre y apellido
            bookState.value = db.bookDao().getBooksByTitle("%${searchQuery.value}%") +
                    db.bookDao().getBooksByAuthorName("%${searchQuery.value}%")
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
                Row {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }

                    Text(
                        text = "Lista de libros",
                        modifier = Modifier.padding(16.dp),
                    )

                    Image(
                        painter = painterResource(id = R.drawable.libro),
                        contentDescription = "Imagen de lista de libros"
                    )
                }

                // Barra de búsqueda
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Buscar libro ...") },
                    singleLine = true,
                    maxLines = 1
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("BookAddScreen") },
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
                if (bookState.value.isEmpty()) {
                    Text("No hay libros disponibles.")
                } else {
                    LazyColumn {
                        items(bookState.value.size) { index ->

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
                                    val book = bookState.value[index]
                                    Text(
                                        text = "${book.id}",
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
                                    val book = bookState.value[index]
                                    Text(
                                        text = "${book.title} (${book.year})\n${book.authorId} ",
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