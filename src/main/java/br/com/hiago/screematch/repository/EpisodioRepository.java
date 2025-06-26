package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
}
