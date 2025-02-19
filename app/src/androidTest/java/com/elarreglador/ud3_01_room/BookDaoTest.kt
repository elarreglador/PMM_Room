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
class BookDaoTest {

    private lateinit var database: MyDatabase
    private lateinit var authorDao: AuthorDao
    private lateinit var bookDao: BookDao

    @Before    // Crea la base de datos en memoria antes de cada prueba
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build() // allowMainThreadQueries para pruebas sin hilos
        authorDao = database.authorDao()
        bookDao = database.bookDao()
    }

    @After    // Borra la base de datos después de cada prueba
    fun tearDown() { database.close() }

    @Test    // Prueba para insertar un libro y verificar si se inserta correctamente
    fun insertBook() = runBlocking {
        // Crear e insertar autor y libro
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        bookDao.insertBook(libro)

        val libroRecuperado = bookDao.getBookById(id)         // Recuperar el libro insertado

        // Verificar que los campos sean correctos
        assertEquals("Harry Potter and the Sorcerer's Stone", libroRecuperado?.title)
        assertEquals(id, libroRecuperado?.authorId)
        assertEquals(1997, libroRecuperado?.year)
    }

    @Test     // Prueba para actualizar un libro y verificar si se actualiza correctamente
    fun updateBook() = runBlocking {
        // Crear e insertar autor y libro
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        var book = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        var bookId = bookDao.insertBook(book)

        // Actualizar el libro
        book = book.copy(id = bookId, title = "Harry Potter and the Chamber of Secrets")
        bookDao.updateBook(book)

        val libroRecuperado = bookDao.getBookById(id)         // Recuperar el libro actualizado

        // Verificar que los campos sean correctos
        assertEquals("Harry Potter and the Chamber of Secrets", libroRecuperado?.title)
    }

    @Test     // Prueba para obtener un libro por su ID
    fun getBookById() = runBlocking {
        // Crear e insertar autor y libro
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997  )
        bookDao.insertBook(libro)

        val libroObtenido = bookDao.getBookById(id)        // obtener libro

        // Verificar que los campos sean correctos
        assertEquals("Harry Potter and the Sorcerer's Stone", libroObtenido?.title)
        assertEquals(id, libroObtenido?.authorId)
        assertEquals(1997, libroObtenido?.year)
    }

    @Test    // Prueba para obtener todos los libros de un autor
    fun getBooksByAuthor() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libro2 = Book(
            title = "Harry Potter and the Chamber of Secrets",
            authorId = id, year = 1998 )
        val libro3 = Book(
            title = "Harry Potter and the Prisoner of Azkaban",
            authorId = id, year = 1999 )
        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)

        val librosDelAutor = bookDao.getBooksByAuthor(id)         // Obtener libros del autor

        // Verificar que los libros sean correctos
        assertEquals(3, librosDelAutor.size)
        assertEquals("Harry Potter and the Sorcerer's Stone", librosDelAutor[0].title)
        assertEquals("Harry Potter and the Chamber of Secrets", librosDelAutor[1].title)
        assertEquals("Harry Potter and the Prisoner of Azkaban", librosDelAutor[2].title)
    }

    @Test     // Prueba para obtener todos los libros
    fun getAllBooks() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libro2 = Book(
            title = "Harry Potter and the Chamber of Secrets",
            authorId = id, year = 1998 )
        val libro3 = Book(
            title = "Harry Potter and the Prisoner of Azkaban",
            authorId = id, year = 1999 )
        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)

        val todosLosLibros = bookDao.getAllBooks()        // Obtener todos los libros

        // Verificar que los libros sean correctos
        assertEquals(3, todosLosLibros.size)
        assertEquals("Harry Potter and the Sorcerer's Stone", todosLosLibros[0].title)
        assertEquals("Harry Potter and the Chamber of Secrets", todosLosLibros[1].title)
        assertEquals("Harry Potter and the Prisoner of Azkaban", todosLosLibros[2].title)
    }

    @Test     // Prueba para obtener libros por nombre de autor
    fun getBooksByAuthorName() = runBlocking {
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
            title = "It", authorId = id2, year = 1986 )

        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)
        bookDao.insertBook(libro4)

        val librosDelAutor = bookDao.getBooksByAuthorName("J.K.")   // Obtener libros del autor

        // Verificar que los libros sean correctos
        assertEquals(3, librosDelAutor.size)
        assertEquals("Harry Potter and the Sorcerer's Stone", librosDelAutor[0].title)
        assertEquals("Harry Potter and the Chamber of Secrets", librosDelAutor[1].title)
        assertEquals("Harry Potter and the Prisoner of Azkaban", librosDelAutor[2].title)
    }

    @Test     // Prueba para obtener libros por apellido de autor
    fun getBooksByAuthorSurname() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libro2 = Book(
            title = "Harry Potter and the Chamber of Secrets",
            authorId = id, year = 1998 )
        val libro3 = Book(
            title = "Harry Potter and the Prisoner of Azkaban",
            authorId = id, year = 1999 )
        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)

        val librosDelAutor = bookDao.getBooksByAuthorSurname("Rowling") // Obtener libros del autor

        // Verificar que los libros sean correctos
        assertEquals(3, librosDelAutor.size)
        assertEquals("Harry Potter and the Sorcerer's Stone", librosDelAutor[0].title)
        assertEquals("Harry Potter and the Chamber of Secrets", librosDelAutor[1].title)
        assertEquals("Harry Potter and the Prisoner of Azkaban", librosDelAutor[2].title)
    }

    @Test     // Prueba para obtener libros por título
    fun getBooksByTitle() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libroId = bookDao.insertBook(libro1)

        // Obtener libros por título
        val librosPorTitulo = bookDao.getBooksByTitle("Harry Potter and the Sorcerer's Stone")

        // Verificar que los libros sean correctos
        assertEquals(1, librosPorTitulo.size)
        assertEquals(libroId, librosPorTitulo[0].id)
    }

    @Test     // Prueba para eliminar un libro
    fun deleteBook() = runBlocking {
        // Crear e insertar autor y libros
        val autor = Author(name = "J.K.", surname = "Rowling", country = "UK")
        val id = authorDao.insertAuthor(autor)
        val libro1 = Book(
            title = "Harry Potter and the Sorcerer's Stone",
            authorId = id, year = 1997 )
        val libroId = bookDao.insertBook(libro1)

        bookDao.deleteBook(libroId)        // Eliminar el libro

        // Verificar que el libro se haya eliminado
        val libroEliminado = bookDao.getBookById(libroId)
        assertNull(libroEliminado)
    }

    @Test     // Prueba para eliminar todos los libros
    fun deleteAllBooks() = runBlocking {
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
            title = "It", authorId = id2, year = 1986 )
        // Insertar libros
        bookDao.insertBook(libro1)
        bookDao.insertBook(libro2)
        bookDao.insertBook(libro3)
        bookDao.insertBook(libro4)

        bookDao.deleteAllBooks()        // Eliminar todos los libros

        // Verificar que no existan libros
        val todosLosLibros = bookDao.getAllBooks()
        assertEquals(0, todosLosLibros.size)
    }
}
