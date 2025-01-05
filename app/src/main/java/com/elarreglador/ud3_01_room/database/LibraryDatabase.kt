package com.elarreglador.ud3_01_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Definición de la base de datos
@Database(entities = [Author::class, Book::class], version = 1, exportSchema = false)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: LibraryDatabase? = null

        // Funcion necesaria para obtener la instancia de la base de datos, es un
        // Singleton para evitar que se creen varias instancias de la base de datos
        // y se llamara solo una vez durante la ejecucion de la app
        fun getDatabase(context: Context): LibraryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDatabase::class.java,
                    "library_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
