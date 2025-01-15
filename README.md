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

TODO: ### Capturas de pantalla

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

## Cómo Empezar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/elarreglador/PMM_Room.git

Ahora eres libre de editar mi app y hacer todas las mejoras que consideres, si son interesantes puedo agregarlas :)