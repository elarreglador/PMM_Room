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
import com.elarreglador.ud3_01_room.database.MyDatabase
import com.elarreglador.ud3_01_room.screens.HomeScreen
import com.elarreglador.ud3_01_room.screens.Navegador
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var miBD: MyDatabase // Permite acceder a la base de datos desde cualquier punto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtén la instancia de la base de datos (Singleton)
        // val miBD = myDatabase.getDatabase(this)

        // Crea un autor
        // val author = Author(name = "Howard Phillips", surname = "Lovecraft", country = "EEUU")
        // val book = Book(title = "Dagon", authorId = 1, year = 1919)

        // Añadir el autor a la base de datos
        lifecycleScope.launch {
            //miBD.authorDao().insertAuthor(author)
            //miBD.bookDao().insertBook(book)
        }

        setContent {
            UD3_01_RoomTheme {
                Navegador()
            }
        }
    }
}


