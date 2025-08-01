package br.com.hiago.screematch.service;

import br.com.hiago.screematch.dto.EpisodioDTO;
import br.com.hiago.screematch.dto.SerieDTO;
import br.com.hiago.screematch.model.Episodio;
import br.com.hiago.screematch.model.Serie;
import br.com.hiago.screematch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converterListaDeSeries(repository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converterListaDeSeries(repository.findTop5ByOrderByAvaliacaoDesc());

    }

    public List<SerieDTO> obterLancamentos() {
        return converterListaDeSeries(repository.lancamentosRecentes());
    }

    public SerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getAtores(),
                    s.getPoster(),
                    s.getSinopse()
            );
        }else {
            throw new RuntimeException("Série com id: " + id + " não encontrada");
        }
    }

    public List<EpisodioDTO> obterTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(
                            e.getTemporada(),
                            e.getTitulo(),
                            e.getNumeroEpisodio(),
                            e.getDataLancamento()))
                    .collect(Collectors.toList());
        }else {
            throw new RuntimeException("Episodio não encontrado");
        }
    }


    private List<SerieDTO> converterListaDeSeries(List<Serie> serieList){
        return serieList.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse()))
                .collect(Collectors.toList());
    }
}