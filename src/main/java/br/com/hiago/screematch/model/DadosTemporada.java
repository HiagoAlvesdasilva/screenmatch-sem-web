package br.com.hiago.screematch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numeroDeTemporadas,

                             @JsonAlias("Episodes") List<DadosEpisodios> episodios) {
}
