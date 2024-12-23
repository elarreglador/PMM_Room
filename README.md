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

## Estructura del Proyecto

- **Entidades**: Representan las tablas en la base de datos Room (por ejemplo, `Book`).
- **DAO**: Interfaces para interactuar con la base de datos (Data Access Object).
- **ViewModel**: Gestiona los datos para la interfaz de usuario respetando el ciclo de vida.
- **UI**: Diseñada usando Jetpack Compose para una experiencia moderna y fluida.

## Cómo Empezar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/elarreglador/PMM_Room.git
