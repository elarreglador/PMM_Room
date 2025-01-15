package com.elarreglador.ud3_01_room.screens

import android.R.attr.enabled
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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

    var context = LocalContext.current
    var miBD = MyDatabase.getDatabase(context)
    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }
    val isSwitchEnabled = remember { mutableStateOf(false) } // Estado del Switch

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
                        text = "Agregar Autor",
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

                Image(
                    painter = painterResource(id = R.drawable.autor),
                    contentDescription = "Imagen de autor"
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

                Spacer (modifier = Modifier.weight(1f))

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                        .border(
                            width = 3.dp,
                            color = Color.Red
                        )

                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 10.dp,
                            end = 10.dp,
                            bottom = 0.dp
                        )
                    ) {
                        Switch(
                            checked = isSwitchEnabled.value,
                            onCheckedChange = { isSwitchEnabled.value = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ATENCION: Desde aqui se habilita el boton rojo para borrado masivo de la base de datos de autores y libros.'",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    FloatingActionButton(
                        onClick = if (isSwitchEnabled.value) {
                            {
                                val dbName = "myBD"
                                val result = context.deleteDatabase(dbName)
                                if (result) {
                                    println("Base de datos borrada exitosamente.")
                                } else {
                                    println("No se pudo borrar la base de datos o no existe.")
                                }
                            }
                        } else {
                            {} // When switch is off, do nothing on click
                        },
                        containerColor = if (isSwitchEnabled.value) {
                            Color.Red
                        } else {
                            Color.Gray
                        },
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(16.dp)
                            .alpha(if (isSwitchEnabled.value) 1f else 0f) // Invisible cuando el switch está apagado
                            .then(
                                if (isSwitchEnabled.value) Modifier else Modifier.height(0.dp).width(0.dp) // No especificas el tamaño cuando el switch está encendido
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar Base de Datos",
                                modifier = Modifier.size(80.dp)
                            )
                            Text(text = "BORRAR TODOS LOS AUTORES Y LIBROS")
                        }

                    }
                }

            }
        }
    )
}