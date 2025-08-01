package br.com.hiago.screematch.controller;

import br.com.hiago.screematch.dto.EpisodioDTO;
import br.com.hiago.screematch.dto.SerieDTO;
import br.com.hiago.screematch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping()
    public List<SerieDTO> buscarTodasAsSeries(){
        return serieService.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> buscarTop5Series(){
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> BuscarSeriesLancamentos(){
        return serieService.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO buscarSeriePorId(@PathVariable Long id){
        return serieService.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> buscarTodasAsTemporadas(@PathVariable Long id){
        return serieService.obterTemporadas(id);
    }
}
