package com.alura.literatura.repository;

import com.alura.literatura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository <Libro, Long> {
    Optional<Libro> findByTitulo(String nombre);

    @Query("SELECT b FROM Libro b WHERE b.lenguaje = :lan")
    List<Libro> filterLibrosByLanguage(String lan);
}
