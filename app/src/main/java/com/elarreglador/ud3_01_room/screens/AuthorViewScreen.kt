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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
fun AuthorViewScreen(navController: NavController , bookId: Int) {
    var context = LocalContext.current
    var miBD = MyDatabase.getDatabase(context)
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }

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
            ) {

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

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {

                    Button(onClick = {
                        navController.navigate("AuthorEditScreen/$bookId")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            modifier = androidx.compose.ui.Modifier.size(20.dp)
                        )
                        Text(" Editar autor")
                    }
                }

            }
        }
    )
}