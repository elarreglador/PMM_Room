package com.elarreglador.ud3_01_room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Definición de la interfaz para el DAO de autores
@Dao
interface AuthorDao {

    @Insert
    suspend fun insertAuthor(author: Author): Long // Insertar autor

    @Update
    suspend fun updateAuthor(author: Author) // Actualizar autor

    @Query("SELECT * FROM authors WHERE id = :authorId")
    suspend fun getAuthorById(authorId: Long): Author? // Buscar autor por id

    @Query("SELECT * FROM authors")
    suspend fun getAllAuthors(): List<Author> // Buscar todos los autores

    @Query("SELECT * FROM authors WHERE name LIKE :authorName")
    suspend fun getAuthorsByName(authorName: String): List<Author>  // Buscar por nombre

    @Query("SELECT * FROM authors WHERE surname LIKE :authorSurname")
    suspend fun getAuthorsBySurname(authorSurname: String): List<Author>  // Buscar por apellido

    @Query("""
        SELECT authors.* FROM authors
        INNER JOIN books ON authors.id = books.authorId
        WHERE books.title LIKE :bookTitle
    """)
    suspend fun getAuthorByBookTitle(bookTitle: String): List<Author>  // Buscar autor por libro

    @Query("DELETE FROM authors WHERE id = :authorId")
    suspend fun deleteAuthor(authorId: Long) // Eliminar autor por id

    @Query("DELETE FROM authors")
    suspend fun deleteAllAuthors() // Eliminar todos los autores
}
