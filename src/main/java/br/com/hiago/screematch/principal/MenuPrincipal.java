package br.com.hiago.screematch.principal;

import br.com.hiago.screematch.model.DadosEpisodios;
import br.com.hiago.screematch.model.DadosSerie;
import br.com.hiago.screematch.model.DadosTemporada;
import br.com.hiago.screematch.model.Episodio;
import br.com.hiago.screematch.service.ConsumoApi;
import br.com.hiago.screematch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        System.out.println("\n Top 10 episodios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println(" Primeiro filtro (N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodios:: avaliacao).reversed())
                .peek(e -> System.out.println(" Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println(" Limitando em 10: " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println(" Mapeando e Alterando formatação para Maiusculo " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroDeTemporadas(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite o nome do Episodio que deseja buscar: ");
        var trechoTitulo = leitura.nextLine();

        List<Episodio> episodiosBuscados = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .collect(Collectors.toList());

        if (!episodiosBuscados.isEmpty()){
            System.out.println("\nEpisodios encontrados: ");
            episodiosBuscados.forEach(e -> System.out.println("Temporada: " + e.getTempodada() + " | Titulo: " + e.getTitulo()));
        } else {
            System.out.println("Episodio não encontrado!");
        }

        System.out.println("A partir de que ano você deseja ver os episodios: ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e  -> e != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        " Tempodara: " + e.getTempodada() +
                                " Episodio: " + e.getTitulo() +
                                " Numero do Episodio: " + e.getNumeroEpisodio()+
                                " Data de Lançamento: " + e.getDataLancamento().format(dateTimeFormatter)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTempodada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println("Avaliaçao das temporadas: " + avaliacoesPorTemporada);

    }
}
