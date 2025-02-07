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
    fun setup() {
        // Usamos una base de datos en memoria
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build() // allowMainThreadQueries para pruebas sin hilos
        authorDao = database.authorDao()
        bookDao = database.bookDao()
    }

    // Borra la base de datos después de cada prueba
    @After
    fun tearDown() {
        database.close()
    }


    // Prueba para insertar un autor y verificar si se inserta correctamente
    @Test
    fun insertAuthor() = runBlocking {
        val author = Author(name = "J.K.", surname = "Rowling", country = "UK")

        // Inserta el autor y obtiene el id generado
        val id = authorDao.insertAuthor(author)

        // Actualiza el objeto Author con el id generado
        val insertedAuthor = authorDao.getAuthorById(id)

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
        val updatedAuthor = author.copy(id = id, country = "USA")  // Usar el id generado para la actualización
        authorDao.updateAuthor(updatedAuthor)

        val retrievedAuthor = authorDao.getAuthorById(id)  // Usar el id generado para recuperar el autor
        assertEquals(updatedAuthor.country, retrievedAuthor?.country)
    }

    // Prueba para obtener un autor por id
    @Test
    fun getAuthorById() = runBlocking {
        // Inserta un autor
        val author = Author(
            name = "J.K.",
            surname = "Rowling",
            country = "UK"
        )
        val id = authorDao.insertAuthor(author)

        // Prueba el metodo getAuthorById
        val result = authorDao.getAuthorById(id)

        // el author creado tiene id=0, pero Room genera ID=1
        assertEquals(author.copy(id = id), result)
    }

    @Test
    fun getAllAuthors() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "Aldous", surname = "Huxley", country = "UK")
        val id1 = authorDao.insertAuthor(author1)
        val id2 = authorDao.insertAuthor(author2)

        // Obtener todos los autores
        val authors = authorDao.getAllAuthors()

        // Verificar que se hayan obtenido correctamente
        assertEquals(2, authors.size)
        assertEquals(author1.copy(id = id1), authors[0])
        assertEquals(author2.copy(id = id2), authors[1])
    }

    @Test
    fun getAuthorsByName() = runBlocking {
        val author1 = Author(name = "George", surname = "Orwell", country = "UK")
        val author2 = Author(name = "George", surname = "R. R. Martin", country = "USA")
        val id1 = authorDao.insertAuthor(author1)
        val id2 = authorDao.insertAuthor(author2)

        // Buscar autores por nombre
        val authors = authorDao.getAuthorsByName("George")

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

        // Buscar autores por apellido
        val authors = authorDao.getAuthorsBySurname("Orwell")

        // Verificar que se hayan obtenido correctamente
        assertEquals(1, authors.size)
        assertEquals(author1.copy(id = id1), authors[0])
    }

    @Test
    fun getAuthorByBookTitle() = runBlocking {
        val author = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val idAut = authorDao.insertAuthor(author)
        val libro = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = idAut,
            year = 1997
        )
        bookDao.insertBook(libro)

        // Buscar autor por título de libro
        val authors = authorDao.getAuthorByBookTitle("Harry Potter")

        assertEquals(idAut, authors[0].id)  // Comparamos IDs
        assertEquals(author.name, authors[0].name)  // Opcional: validar el nombre
        assertEquals(author.surname, authors[0].surname)  // Opcional: validar el apellido

    }

        // Prueba para eliminar un autor
    @Test
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
