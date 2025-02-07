package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elarreglador.ud3_01_room.R
import com.elarreglador.ud3_01_room.database.MyDatabase

@Composable
fun BookViewScreen(navController: NavController, bookId: Int) {
    var context = LocalContext.current
    var miBD = MyDatabase.getDatabase(context)
    var title = remember { mutableStateOf("") }
    var authorId = remember { mutableStateOf("") }
    var year = remember { mutableStateOf("") }
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }

    // Llamada asíncrona para obtener los datos del libro
    LaunchedEffect(bookId) {
        val book = miBD.bookDao().getBookById(bookId.toLong()) // Convierte a Long si necesario
        book?.let {
            title.value = it.title
            authorId.value = it.authorId.toString() // Cambia según cómo guardes datos de autor
            year.value = it.year.toString() // Cambia según el campo correspondiente
        }

        // Obtener el nombre del autor a partir de su ID
        val escritor = miBD.authorDao().getAuthorById(authorId.value.toLong())
        // ?. asegura que el bloque solo se ejecutará si escritor no es null.
        escritor?.let {
            name.value = it.name
            surname.value = it.surname
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 0.dp, top = 25.dp, end = 0.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = { navController.navigate("BookListViewScreen") },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }

                    Text(
                        text = "Informacion del libro",
                        modifier = Modifier.padding(16.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.libro),
                        contentDescription = "Imagen de libro"
                    )
                }
            }
        },
        bottomBar = {},
        floatingActionButton = {},
        content = { paddingValues ->
            // Contenido principal, respetando los paddings del Scaffold
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues) // Aplica el padding del Scaffold
                    .padding(16.dp) // Agrega un padding adicional si es necesario
            ) {

                Spacer (modifier = Modifier.height(10.dp))

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                ){
                    Box( // espacio para la id del libro
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiary)
                            .widthIn(min = 60.dp)
                    ) {
                        Text(
                            text = ("Book ID: ${bookId}"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = ("Titulo: ${title.value}"),
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Autor: ${name.value} ${surname.value}",
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Fecha primera edicion: ${year.value}",
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )
                }

                Spacer (modifier = Modifier.height(10.dp))

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {

                    Button(onClick = {
                        navController.navigate("BookEditScreen/$bookId")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Editar",
                            modifier = androidx.compose.ui.Modifier.size(20.dp)
                        )
                        Text(" Editar libro")
                    }

                    Button(onClick = {
                        navController.navigate("BookListViewScreen")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Ok",
                            modifier = androidx.compose.ui.Modifier.size(20.dp)
                        )
                        Text(" Ok")
                    }
                }

            }
        }
    )
}