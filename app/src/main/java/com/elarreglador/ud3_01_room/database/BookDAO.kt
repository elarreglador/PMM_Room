package com.elarreglador.ud3_01_room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Definición de la interfaz para el DAO de libros
@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: Book) // Insertar libro

    @Update
    suspend fun updateBook(book: Book) // Actualizar libro

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): Book? // Buscar libro por id

    @Query("SELECT * FROM books WHERE id = :authorId")
    suspend fun getBooksByAuthor(authorId: Long): List<Book> // Buscar libros por id de autor

    @Query("""
        SELECT books.* FROM books
        INNER JOIN authors ON books.authorId = authors.id
        WHERE authors.name LIKE :authorName
    """)
    suspend fun getBooksByAuthorName(authorName: String): List<Book>  // Buscar libros por autor

    @Query("SELECT * FROM books WHERE title LIKE :bookTitle")  // Buscar por título del libro
    suspend fun getBooksByTitle(bookTitle: String): List<Book>

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: Long) // Eliminar libro por id
}
