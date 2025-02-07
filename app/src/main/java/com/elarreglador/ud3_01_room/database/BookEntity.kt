package com.elarreglador.ud3_01_room.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Definición de la entidad libro
@Entity(
    tableName = "books",
    foreignKeys = [ForeignKey( // creamos una clave foranea que referencia
        entity = Author::class, // a la tabla autor
        parentColumns = ["id"], //  y relaciona la id del autor
        childColumns = ["authorId"], // con authorId del libro.
        onDelete = ForeignKey.CASCADE //si se elimina el autor se elimina el libro
    )],
    indices = [Index("authorId")] // indice para la clave foranea que mejora el rendimiento
)

data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val authorId: Long, // clave foranea a la id de autor
    val year: Int
)
