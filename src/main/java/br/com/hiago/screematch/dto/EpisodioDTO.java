package br.com.hiago.screematch.dto;

import java.time.LocalDate;

public record EpisodioDTO(
        Integer temporada,
        String titulo,
        Integer numeroEpisodio,
        LocalDate dataLancamento) {
}
