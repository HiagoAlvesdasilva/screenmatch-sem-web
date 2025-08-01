package br.com.hiago.screematch.dto;

import java.time.LocalDate;

public record EpisodioDTO(
        Integer tempodada,
        String titulo,
        Integer numeroEpisodio,
        LocalDate dataLancamento) {
}
