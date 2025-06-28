package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Episodio;
import br.com.hiago.screematch.model.Serie;
import br.com.hiago.screematch.model.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {


    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    @Query( "select s from Serie s where s.totalTemporadas <= :temporadas and s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadasEAvaliacao(int temporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodioPorTrecho(String trechoEpisodio );

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC")
    List<Episodio> topEpisodiosPorSerie(@Param("serie") Serie serie, Pageable pageable);

}
