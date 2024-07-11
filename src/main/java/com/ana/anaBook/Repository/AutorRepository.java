package com.ana.anaBook.Repository;

import com.ana.anaBook.Models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnoFallecimientoIsNullAndAnoNacimientoLessThanEqual(Integer ano);
}
