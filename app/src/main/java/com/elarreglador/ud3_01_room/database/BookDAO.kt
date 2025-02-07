package com.elarreglador.ud3_01_room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Definición de la interfaz para el DAO de libros
@Dao
interface BookDao {

    // Insertar libro
    @Insert
    suspend fun insertBook(book: Book): Long

    // Actualizar libro
    @Update
    suspend fun updateBook(book: Book)

    // Buscar libro por id
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): Book?

    // Buscar libros por id de autor
    @Query("SELECT * FROM books WHERE authorId = :authorId")
    suspend fun getBooksByAuthor(authorId: Long): List<Book>

    // Buscar todos los libros
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>

    // Buscar libros por autor
    @Query("""
        SELECT books.* FROM books
        INNER JOIN authors ON books.authorId = authors.id
        WHERE authors.name LIKE :authorName
    """)
    suspend fun getBooksByAuthorName(authorName: String): List<Book>

    // Buscar libros por autor
    @Query("""
        SELECT books.* FROM books
        INNER JOIN authors ON books.authorId = authors.id
        WHERE authors.surname LIKE :authorSurname
    """)
    suspend fun getBooksByAuthorSurname(authorSurname: String): List<Book>

    // Buscar por título del libro
    @Query("SELECT * FROM books WHERE title LIKE :bookTitle")
    suspend fun getBooksByTitle(bookTitle: String): List<Book>

    // Eliminar libro por id
    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: Long)

    // Eliminar todos los autores
    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
