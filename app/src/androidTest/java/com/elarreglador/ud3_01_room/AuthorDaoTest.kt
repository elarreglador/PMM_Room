package com.elarreglador.ud3_01_room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import com.elarreglador.ud3_01_room.database.MyDatabase
import com.elarreglador.ud3_01_room.database.Author
import com.elarreglador.ud3_01_room.database.AuthorDao
import com.elarreglador.ud3_01_room.database.Book
import com.elarreglador.ud3_01_room.database.BookDao

@RunWith(AndroidJUnit4::class)
class AuthorDaoTest {

    private lateinit var database: MyDatabase
    private lateinit var authorDao: AuthorDao
    private lateinit var bookDao: BookDao

    // Crea la base de datos en memoria antes de cada prueba
    @Before
    fun setup() {         // Usamos una base de datos en memoria
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build() // allowMainThreadQueries para pruebas sin hilos
        authorDao = database.authorDao()
        bookDao = database.bookDao()
    }

    @After     // Borra la base de datos después de cada prueba
    fun tearDown() { database.close() }

    @Test     // Prueba para insertar un autor y verificar si se inserta correctamente
    fun insertAuthor() = runBlocking {
        val author = Author(name = "J.K.", surname = "Rowling", country = "UK")

        val id = authorDao.insertAuthor(author)  // Inserta el autor y obtiene el id generado

        val insertedAuthor = authorDao.getAuthorById(id) // Recupera Author con el id generado

        // Verifica que los campos sean correctos
        assertEquals("J.K.", insertedAuthor?.name)
        assertEquals("Rowling", insertedAuthor?.surname)
        assertEquals("UK", insertedAuthor?.country)
    }

    @Test
    fun updateAuthor() = runBlocking {
        val author = Author(name = "J.R.R.", surname = "Tolkien", country = "UK")
        val id = authorDao.insertAuthor(author)  // Obtener el id generado después de insertar

        // Actualizamos el autor
        val updatedAuthor = author.copy(
            id = id,
            name = "Juan Rigoberto Ronaldo",
            surname = "To-kien",
            country = "Estados juntitos")
        authorDao.updateAuthor(updatedAuthor)

        val retrievedAuthor = authorDao.getAuthorById(id)
        assertEquals(updatedAuthor.country, retrievedAuthor?.country)
        assertEquals(updatedAuthor.name, retrievedAuthor?.name)
        assertEquals(updatedAuthor.surname, retrievedAuthor?.surname)
    }

    @Test    // Prueba para obtener un autor por id
    fun getAuthorById() = runBlocking {
        // Inserta un autor
        val author = Author(
            name = "J.K.",
            surname = "Rowling",
            country = "UK"
        )
        val id = authorDao.insertAuthor(author)

        val result = authorDao.getAuthorById(id)   // Prueba el metodo getAuthorById

        assertEquals(author.copy(id = id), result) // Verifica que el resultado sea el esperado
    }

    @Test
    fun getAllAuthors() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "Aldous", surname = "Huxley", country = "UK")
        val id1 = authorDao.insertAuthor(author1)
        val id2 = authorDao.insertAuthor(author2)

        val authors = authorDao.getAllAuthors()        // Obtener todos los autores

        // Verificar que se hayan obtenido correctamente
        assertEquals(2, authors.size)
        assertEquals(author1.copy(id = id1), authors[0])
        assertEquals(author2.copy(id = id2), authors[1])
    }

    @Test
    fun getAuthorsByName() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "George", surname = "R. R. Martin", country = "USA")
        val author3 = Author(name = "Aldous", surname = "Huxley", country = "UK")
        val id1 = authorDao.insertAuthor(author1)
        val id2 = authorDao.insertAuthor(author2)
        val id3 = authorDao.insertAuthor(author3)

        val authors = authorDao.getAuthorsByName("George")        // Buscar autores por nombre

        // Verificar que se hayan obtenido correctamente
        assertEquals(2, authors.size)
        assertEquals(author1.copy(id = id1), authors[0])
        assertEquals(author2.copy(id = id2), authors[1])
    }

    @Test
    fun getAuthorsBySurname() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "Aldous", surname = "Huxley", country = "UK")
        val id1 = authorDao.insertAuthor(author1)
        val id2 = authorDao.insertAuthor(author2)

        val authors = authorDao.getAuthorsBySurname("Orwell")        // Buscar autores por apellido

        assertEquals(1, authors.size) // verificar numero de autores
        assertEquals(author1.copy(id = id1), authors[0]) // verificar autor
    }

    @Test
    fun getAuthorByBookTitle() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val autor2 = Author(name = "Stephen", surname = "King", country = "USA")
        val id2 = authorDao.insertAuthor(autor2)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libro2 = Book(
            title = "Harry Potter and the Chamber of Secrets",
            authorId = id, year = 1998 )
        val libro3 = Book(
            title = "Harry Potter and the Prisoner of Azkaban",
            authorId = id, year = 1999 )
        val libro4 = Book(
            title = "It", authorId = id2, year = 1986  )

        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)
        bookDao.insertBook(libro4)

        // Buscar autor por título de libro
        val authors = authorDao.getAuthorByBookTitle("Harry Potter")

        // Comparamos IDs
        assertEquals(id, authors[0].id)
        assertEquals(id, authors[1].id)
        assertEquals(id, authors[2].id)
    }

    @Test         // Prueba para eliminar un autor
    fun deleteAuthor() = runBlocking {
        val author = Author(name = "Agatha", surname = "Christie", country = "UK")
        authorDao.insertAuthor(author)

        authorDao.deleteAuthor(author.id)
        val deletedAuthor = authorDao.getAuthorById(author.id)
        assertNull(deletedAuthor)
    }

    @Test
    fun deleteAllAuthors() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "Aldous", surname = "Huxley", country = "UK")
        authorDao.insertAuthor(author1)
        authorDao.insertAuthor(author2)

        // Eliminar todos los autores
        authorDao.deleteAllAuthors()

        val authors = authorDao.getAllAuthors()
        assertEquals(0, authors.size)
    }
}
