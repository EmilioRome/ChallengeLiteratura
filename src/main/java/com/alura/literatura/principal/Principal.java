package com.alura.literatura.principal;

import com.alura.literatura.modelo.Autor;
import com.alura.literatura.modelo.DatosLibro;
import com.alura.literatura.modelo.Libro;
import com.alura.literatura.modelo.ResultadosData;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_API = "https://gutendex.com/books/?search=";
    ConvierteDatos conversor = new ConvierteDatos();

    private int option = -1;
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void menu() {
        while (option != 0) {
            String MENU = """
                    Selecciona la opción ingresando el número correspondiente:
                    1- Buscar libro por título.
                    2- Listar libros registrados.
                    3- Listar autores registrados.
                    4- Listar autores vivos en un determinado año.
                    5- Listar libros por idioma.
                    0- Salir.
                    """;
            System.out.println(MENU);
            option = teclado.nextInt();
            teclado.nextLine();

            switch (option) {
                case 1:
                    buscarLibroApi();
                    break;
                case 2:
                    librosBuscados();
                    break;
                case 3:
                    autoresBuscados();
                    break;
                case 4:
                    autoresVivosPorFecha();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la app...");
                    break;
                default:
                    System.out.println("Opción invalida");
                    break;
            }
        }
    }

    private void buscarLibroApi() {

        DatosLibro book = buscaLibroPortitulo();

        if (book == null){
            System.out.println("Libro no encontrado\n");
            return;
        }

        Autor autor = new Autor(book.autores().get(0));
        Libro libro = new Libro(book, autor);

        String tituloLibro = libro.getTitulo();
        Optional<Libro> libroExistente = libroRepositorio.findByTitulo(tituloLibro);
        if (libroExistente.isPresent()){
            System.out.println("El libro ya esta registrado en la base de datos\n");
            return;
        }

        String nombreAutor = libro.getAutor().getNombre();
        Optional<Autor> autorExistente = autorRepositorio.findByNombre(nombreAutor);

        if(autorExistente.isPresent()){
            libro.setAutor(autorExistente.get());
        }else {
            autorRepositorio.save(autor);
        }

        libroRepositorio.save(libro);

        System.out.println(libro.toString());
    }

    private DatosLibro buscaLibroPortitulo (){
        System.out.println("Ingrese el titulo del libro que desea encontrar: ");
        String tituloIngresado = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_API + tituloIngresado.toLowerCase().replace(" ", "+"));

        var datos = conversor.obtenerDatos(json, ResultadosData.class);

        if (datos.Libros().isEmpty()){
            return null;
        } else {
            Optional<DatosLibro> libro = datos.Libros().stream().findFirst();

            return libro.orElse(null);
        }
    }

    private void librosBuscados(){
        List<Libro> libros = libroRepositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            System.out.println("Libros encontrados:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void autoresBuscados(){
        List<Autor> autores = autorRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
        } else {
            System.out.println("Autores encontrados:");
            for (Autor autor : autores) {
                System.out.println(autor.toString());
            }
        }
    }

    public void autoresVivosPorFecha() {
        System.out.println("Ingresa el año bajo el cual quieres consultar que autores vivieron: ");
        int year = teclado.nextInt();
        teclado.nextLine();

        List<Autor> filteredAuthors = autorRepositorio.filterAutoresByYear(year);
        filteredAuthors.forEach(System.out::println);
    }

    public void buscarLibrosPorIdioma() {
        List<String> lenguajes = List.of("es", "en", "fr");
        String idiomaMenu = """
                Ingrese el idioma para buscar los libros:
                es - Español
                en - Inglés
                fr - Francés
                """;
        System.out.println(idiomaMenu);
        String userLan = teclado.nextLine();

        while (!lenguajes.contains(userLan)) {
            System.out.println("Opción invalida, ingresa un idioma de la lista: ");
            userLan = teclado.nextLine();
        }
        List<Libro> dbBooks = libroRepositorio.filterLibrosByLanguage(userLan);

        if (dbBooks.isEmpty()) {
            System.out.println("No hay libros registrados con este idioma \n");
        } else {
            dbBooks.forEach(System.out::println);
        }
    }
}
