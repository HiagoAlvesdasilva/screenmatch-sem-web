package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Serie;
import br.com.hiago.screematch.model.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {


    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findByTotalTemporadasAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(Integer temporadas, Double avaliacao);
}
