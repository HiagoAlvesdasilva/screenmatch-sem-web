package br.com.hiago.screematch.repository;

import br.com.hiago.screematch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

}
