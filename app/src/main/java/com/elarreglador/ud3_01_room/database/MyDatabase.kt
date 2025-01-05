package com.elarreglador.ud3_01_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Definición de la base de datos
@Database(entities = [Author::class, Book::class], version = 1, exportSchema = false)
abstract class myDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: myDatabase? = null

        // Funcion necesaria para obtener la instancia de la base de datos, es un
        // Singleton para evitar que se creen varias instancias de la base de datos
        // y se llamara solo una vez durante la ejecucion de la app
        fun getDatabase(context: Context): myDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    myDatabase::class.java,
                    "my_Database"
                )
                    // Room regenera las tablas de la base de datos en cada ejecucion
                    .fallbackToDestructiveMigration()
                    .build() // Construye la base de datos
                INSTANCE = instance
                instance
            }
        }
    }
}
