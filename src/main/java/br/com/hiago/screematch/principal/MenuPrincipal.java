package br.com.hiago.screematch.principal;

import br.com.hiago.screematch.model.DadosEpisodios;
import br.com.hiago.screematch.model.DadosSerie;
import br.com.hiago.screematch.model.DadosTemporada;
import br.com.hiago.screematch.model.Episodio;
import br.com.hiago.screematch.service.ConsumoApi;
import br.com.hiago.screematch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class MenuPrincipal {

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=74979642";

    public void exibeMenu(){
        System.out.println("Digite o nome de série que deseja buscar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(
                ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i =1; i <= dadosSerie.totalTemporadas(); i++){
            json = consumoApi.obterDados(
                    ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios:: avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroDeTemporadas(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }

}
