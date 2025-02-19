package com.elarreglador.ud3_01_room.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun AuthorViewScreen(navController: NavController , authorId: Int) {
    var context = LocalContext.current
    var miBD = MyDatabase.getDatabase(context)
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }

    LaunchedEffect(authorId) {
        // Obtener el nombre del autor a partir de su ID
        val escritor = miBD.authorDao().getAuthorById(authorId.toLong())
        // ?. asegura que el bloque solo se ejecutará si escritor no es null.
        escritor?.let {
            name.value = it.name
            surname.value = it.surname
            country.value = it.country
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
                        onClick = { navController.navigate("AuthorListViewScreen") },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }

                    Text(
                        text = "Informacion del Autor",
                        modifier = Modifier.padding(16.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.autor),
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
                    .fillMaxSize()
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
                            text = ("author ID: ${authorId}"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                        )
                    }

                    Spacer (modifier = Modifier.height(10.dp))

                    Text(
                        text = "Nombre: ${name.value}",
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Apellidos: ${surname.value}",
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Pais: ${country.value}",
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
                        navController.navigate("AuthorEditScreen/$authorId")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Editar",
                            modifier = androidx.compose.ui.Modifier.size(20.dp)
                        )
                        Text(" Editar autor")
                    }

                    Button(onClick = {
                        navController.navigate("AuthorListViewScreen")
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