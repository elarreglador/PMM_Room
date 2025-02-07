# PMM_Room: Biblioteca Personal

**Biblioteca Personal** es un proyecto desarrollado como ejercicio práctico para aprender y comprender el funcionamiento de Room, una librería de persistencia de datos en Android. Este proyecto busca ofrecer una manera sencilla y eficiente de gestionar una colección de libros, con funcionalidades para añadir, actualizar, eliminar y visualizar registros de libros, así como las relaciones entre ellos.

## Objetivos del Proyecto

- **Aprender Room**: Comprender el uso de Room en Android, incluyendo la creación de entidades, DAOs y relaciones.
- **Gestión de datos**: Permitir al usuario realizar operaciones CRUD (Crear, Leer, Actualizar y Borrar) sobre los registros de libros.
- **Relaciones entre datos**: Explorar y gestionar relaciones, como por ejemplo, entre libros y autores o géneros.

## Funcionalidades Principales

1. **Añadir libros**:
    - Registrar información básica como título, autor, género y año de publicación.

2. **Actualizar registros**:
    - Modificar los detalles de un libro existente.

3. **Eliminar libros**:
    - Borrar un libro de la colección con soporte para la eliminación en cascada si existen relaciones asociadas.

4. **Visualizar colección**:
    - Mostrar una lista de libros con sus detalles en un formato organizado.

5. **Gestión de relaciones**:
    - Implementar y explorar relaciones, como "muchos a uno" (varios libros pueden tener el mismo autor) y "muchos a muchos" (libros que pertenecen a varios géneros).

## Commit recomendado para entender Room: 0538d1d

Este commit ( 0538d1d ) contiene el funcionamiento basico de Room, la biblioteca de gestion de bases de datos 0538d1d

Al iniciar la app se crea una instancia singleton ( https://es.wikipedia.org/wiki/Singleton ) de la BD, se introduce un autor y un libro. FIN

Por pantalla no se muestra nada, pero se puede confirmar la escritura en database inspector ( https://developer.android.com/studio/inspect/database ). y a partir de este punto se pueden emplear las funciones que existen en el DAO para cada entidad.

Contiene las entidades y DAO de libro y author ademas de acceso singleton a la base de datos
## Estructura del Proyecto

- **Entidades**: Representan las tablas en la base de datos Room (por ejemplo, `Book`).
- **DAO**: Interfaces para interactuar con la base de datos (Data Access Object).
- **UI**: Diseñada usando Jetpack Compose para una experiencia moderna y fluida.

## Pantallas y navegacion

- **HomeScreen**: Pantalla de inicio de la app
- **AuthorListViewScreen**: Listado de autores
- **BookListViewScreen**: Listado de libros
- **AuthorAddScreen**: Pantalla para agregar autores
- **BookAddScreen**: Pantalla para agregar libros
- **AuthorEditScren**: Edicion de autore
- **BookEditScreen**: Edicion de libro
- **AuthorViewScreen**: Informacion del autor
- **BookViewScreen**: Informacion del libro

Desde HomeScreen se puede acceder a la lista de autores o de libros, desde las listas podemos ir a agregar autor, editar autor o la info. del autor. Tambien existe navegacion entre estas tres ultimas.

```
HomeScreen
   AuthorListViewScreen
      AuthorAddScreen
      AuthorEditScren
      AuthorViewScreen
   BookListViewScreen
      BookAddScreen
      BookEditScreen
      BookViewScreen
```

### Capturas de pantalla

<table>
  <tr>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/HomeScreen.png?raw=true" alt="HomeScreen" width="200" title="HomeScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/AuthorListViewScreen.png?raw=true" alt="AuthorListViewScreen" width="200" title="AuthorListViewScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/AuthorAddScreen.png?raw=true" alt="AuthorAddScreen" width="200" title="AuthorAddScreen"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/AuthorEditScreen.png?raw=true" alt="AuthorEditScreen" width="200" title="AuthorEditScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/AuthorViewScreen.png?raw=true" alt="AuthorViewScreen" width="200" title="AuthorViewScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/BookListViewScreen.png?raw=true" alt="BookListViewScreen" width="200" title="BookListViewScreen"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/BookAddScreen.png?raw=true" alt="BookAddScreen" width="200" title="BookAddScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/BookEditScreen.png?raw=true" alt="BookEditScreen" width="200" title="BookEditScreen"></td>
    <td><img src="https://github.com/elarreglador/PMM_Room/blob/main/SCREENSHOTS/BookViewScreen.png?raw=true" alt="BookViewScreen" width="200" title="BookViewScreen"></td>
  </tr>
</table>

## Zonas de codigo interesantes
### Room (Base de datos)

**MyDatabase** es un archivo que contiene getDatabase, esta funcion llama a la base de datos donde tendremos nuestros libros y autores.

**AuthorEntity** contiene la definicion de la entidad (tabla) author con sus correspondientes campos (name, surname, etc)

**BookEntity** contiene la definicion de la entidad (tabla) book con sus correspondientes campos (title, authorId, etc)

**AuthorDAO** contiene las funciones relativas a author como son agregar autor, editarlo, listar segun id, listar todos, etc

**BookDAO** contiene las funciones relativas a libro como son agregar libro, editarlo, listar por autor, etc.

**MainActivity** puedes ver comentado en este archivo la creacion de un autor y un libro de la forma mas basica posible

### Navegacion

**Navegador.kt** contiene la navegacion que se gestiona por medio de la biblioteca Navigation ( https://developer.android.com/develop/ui/compose/navigation?hl=es-419 ), su uso se puede ver en cualquiera de los botones de los listados de un modo similar a este:

                        onClick = { navController.navigate("HomeScreen") },

### Tests

Los tests verifican el correcto funcionamiento de las funciones de los DAO de Author y Book

```
/app/src/androidTest/java/com/elarreglador/ud3_01_room/AuthorDaoTest
    insertAuthor() - inserta autor, lo recupera y compara resultado
    updateAuthor() - inserta autor, lo edita, recupera y compara resultado
    getAuthorById() - inserta autor, lo recupera y compara resultado
    getAllAuthors() - inserta varios autores, los recupera y compara
    getAuthorsByName() - inserta varios autores y recupera uno por su nombre
    getAuthorsBySurname() - inserta varios autores y recupera uno por su apellido
    getAuthorByBookTitle() - inserta varios libros y autores y los recupera por el titulo del libro
    deleteAuthor() - genera, inserta y borra un autor por su id
    deleteAllAuthors() - genera e inserta varios autores y luego borra todos

/app/src/androidTest/java/com/elarreglador/ud3_01_room/BookDaoTest
    insertBook() - inserta autor y libro y recupera el libro
    updateBook() - inserta autor y libro, edita el libro y lo recupera
    getBookById() - inserta autor y libro y lo recupera por su id
    getBooksByAuthor() - inserta autor y varios libros y los recupera por el autor
    getAllBooks() - crea autor y libros y los recupera
    getBooksByAuthorName() - crea autor y libros y los recupera por nombre de autor
    getBooksByAuthorSurname() - crea autor y libros y los recupera por el apellido del autor
    getBooksByTitle() - crea autor y libro y lo recupera por el titulo
    deleteBook() - crea autor y libro y elimina el libro por su id
    deleteAllBooks() - crea varios autores y libros y elimina los libros
```

## Cómo Empezar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/elarreglador/PMM_Room.git

Ahora eres libre de editar mi app y hacer todas las mejoras que consideres, si son interesantes puedo agregarlas :)
