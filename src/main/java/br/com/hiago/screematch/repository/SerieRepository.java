package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository <Serie, Long> {
}
