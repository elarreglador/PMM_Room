package com.elarreglador.ud3_01_room.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definición de la entidad autor
@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val surname: String,
    val country: String,
)
