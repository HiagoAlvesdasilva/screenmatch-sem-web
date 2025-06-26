package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {


    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

}
