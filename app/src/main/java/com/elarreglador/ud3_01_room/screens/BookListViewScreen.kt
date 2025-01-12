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
    val context = LocalContext.current
    val db = remember { MyDatabase.getDatabase(context) }
    val booksWithAuthorsState = remember { mutableStateOf<List<Pair<Book, String>>>(emptyList()) }
    val searchQuery = remember { mutableStateOf("") }

    // Cargar libros y autores al inicio
    LaunchedEffect(Unit) {
        val books = db.bookDao().getAllBooks()
        val booksWithAuthors = books.map { book ->
            val authorName = db.authorDao().getAuthorById(book.authorId)?.name ?: "Autor desconocido"
            book to authorName
        }
        booksWithAuthorsState.value = booksWithAuthors.sortedBy { it.first.title }
    }

    // Actualizar libros y autores según la búsqueda
    LaunchedEffect(searchQuery.value) {
        val books = if (searchQuery.value.isEmpty()) {
            db.bookDao().getAllBooks()
        } else {
            db.bookDao().getBooksByTitle("%${searchQuery.value}%") +
                    db.bookDao().getBooksByAuthorName("%${searchQuery.value}%") +
                    db.bookDao().getBooksByAuthorSurname("%${searchQuery.value}%")
        }
        val booksWithAuthors = books.map { book ->
            val authorName =
                (db.authorDao().getAuthorById(book.authorId)?.name +
                " " +
                db.authorDao().getAuthorById(book.authorId)?.surname)
            book to authorName
        }
        // Eliminar duplicados usando distinctBy filtrando por la ID del libro y los ordena
        val uniqueBooksWithAuthors = booksWithAuthors
            .distinctBy { it.first.id }
            .sortedBy { it.first.title }

        booksWithAuthorsState.value = uniqueBooksWithAuthors
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 0.dp, top = 25.dp, end = 0.dp)
            ) {
                Row {
                    IconButton(
                        onClick = { navController.navigate("HomeScreen") },
                        ) {
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

                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar libro...") },
                    singleLine = true,
                    maxLines = 1
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("BookAddScreen") },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.shadow(
                    elevation = 12.dp,
                    shape = MaterialTheme.shapes.small,
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir",
                )
            }
        },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (booksWithAuthorsState.value.isEmpty()) {
                    Text("No hay libros disponibles.")
                } else {
                    LazyColumn {
                        items(booksWithAuthorsState.value.size) { index ->
                            val (book, authorName) = booksWithAuthorsState.value[index]

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box( // espacio para la id del libro
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.secondary)
                                        .widthIn(min = 60.dp)
                                        .fillMaxHeight()
                                ) {
                                    Column {
                                        Text(
                                            text = "ID:${book.id}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                            modifier = Modifier
                                        )

                                        Button(
                                            onClick = {  },
                                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
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
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(6.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(
                                        text = "${book.title} (${book.year})\n$authorName" ,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    )
}
