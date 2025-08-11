package br.com.hiago.screematch.client.traducao.omdb;

import br.com.hiago.screematch.model.DadosSerie;
import br.com.hiago.screematch.model.DadosTemporada;
import br.com.hiago.screematch.util.ConsumoApi;
import br.com.hiago.screematch.util.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OmdbClient {

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados conversor;

    private static final String ENDERECO = "http://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=74979642";

    public DadosSerie buscarSerie(String titulo) {
        String url = ENDERECO + titulo.replace(" ", "+") + API_KEY;
        String json = consumoApi.obterDados(url);
        return conversor.obterDados(json, DadosSerie.class);
    }

    public List<DadosTemporada> buscarTemporadas(String titulo, int totalTemporadas) {
        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= totalTemporadas; i++) {
            String url = ENDERECO + titulo.replace(" ", "+") + "&season=" + i + API_KEY;
            String json = consumoApi.obterDados(url);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }
        return temporadas;
    }
}
