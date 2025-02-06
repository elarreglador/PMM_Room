package com.elarreglador.ud3_01_room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@SmallTest
class LibroDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var libroDao: LibroDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        libroDao = database.libroDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertarYLeerLibro() {
        val libro = Libro(id = 1, titulo = "1984", autor = "George Orwell")
        libroDao.insertar(libro)

        val libros = libroDao.obtenerTodos()
        assertEquals(1, libros.size)
        assertEquals("1984", libros[0].titulo)
    }
}