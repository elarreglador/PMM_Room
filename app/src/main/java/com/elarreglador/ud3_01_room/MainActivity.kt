package com.elarreglador.ud3_01_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.elarreglador.ud3_01_room.database.Author
import com.elarreglador.ud3_01_room.ui.theme.UD3_01_RoomTheme
import com.elarreglador.ud3_01_room.database.Book
import com.elarreglador.ud3_01_room.database.LibraryDatabase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtén la instancia de la base de datos
        val miBD = LibraryDatabase.getDatabase(this)

        // Crea un autor y un libro (Objetos)
        val author = Author(id = 1, name = "Pedro", surname = "García", country = "España")
        val book = Book(id = 1, title = "Libro de prueba", authorId = 1, year = 2023)

        // Añadir el autor y el libro a la base de datos
        lifecycleScope.launch {
            miBD.authorDao().insertAuthor(author)
            miBD.bookDao().insertBook(book)
        }

        setContent {
            UD3_01_RoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UD3_01_RoomTheme {
        Greeting("Android")
    }
}