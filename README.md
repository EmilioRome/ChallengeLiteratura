# Literatura

### <span style="font-size:larger;">Este proyecto permite al usuario buscar libros utilizando la API [Gutendex](https://gutendex.com/) y guardar los resultados en una base de datos de PostgreSQL.

## Características ✨

- **Buscar Libro:** Permite buscar libros utilizando la API de Gutendex y guarda el primer resultado encontrado en la base de datos.
- **Listar Libros:** Permite imprimir por consola todos los libros guardados en la base de datos.
- **Listar Autores:** Imprime por consola todos los autores guardados en la base de datos.
- **Listar Autores por Año:** Imprime por consola los autores vivos en el año especificado.
- **Listar Libros por Idioma:** Filtra los libros según el idioma especificado y los muestra por consola.

## Requisitos 🚀

Para este proyecto se utilizaron las siguientes tecnologías:
- **Java JDK:** versión 17 o superior, disponible en [Download the Latest Java LTS Free](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Maven:** versión 4 o superior, para la gestión de dependencias y construcción del proyecto.
- **Spring Boot:** versión 3.3.0, configurado a través de [Spring Initializr](https://start.spring.io/)
- **PostgreSQL:** versión 14.12 o superior, como base de datos relacional.
- **IDE:** IntelliJ IDEA, disponible en [JetBrains](https://www.jetbrains.com/es-es/idea/download/), para el desarrollo integrado del proyecto.

## Dependencias para agregar al crear el proyecto en Spring Initializr:
- **Spring Data JPA:** para la integración con la capa de persistencia.
- **Postgres Driver:** para la conexión con la base de datos PostgreSQL.
- **Jackson:** para el manejo de JSON.
