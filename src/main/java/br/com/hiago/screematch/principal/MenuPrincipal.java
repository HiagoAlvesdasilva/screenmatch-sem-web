package br.com.hiago.screematch.principal;

import br.com.hiago.screematch.model.*;
import br.com.hiago.screematch.model.enums.Categoria;
import br.com.hiago.screematch.repository.EpisodioRepository;
import br.com.hiago.screematch.repository.SerieRepository;
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

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository serieRepository;

    private EpisodioRepository episodioRepository;

    private List<Serie> series = new ArrayList<>();

    public MenuPrincipal(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        this.serieRepository = serieRepository;
        this.episodioRepository = episodioRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por titulo
                    5 - Buscar série por ator
                    6 - Buscar séries por avaliação
                    7 - Buscar top 5 séries
                    8 - Buscar séries por categoria ou gênero
                    9 - Buscar Série por quantitade de temporadas e por notas de avaliação
                    0 - Sair                                 
                    """;
            System.out.println(menu);

            String input =leitura.nextLine();

            try {
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e){
                System.out.println("Entrada inválida. Por favor digite um número entre 0 e 6");
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarSeriesPorAvaliacao();
                    break;
                case 7:
                    buscarTop5Series();
                    break;
                case 8:
                    buscarSeriesPorCategoria();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                case 9:
                    buscarSeriesPorTemporadasEAvaliacao();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        serieRepository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma serie pelo nome: ");
        var nomeSerie = leitura.nextLine().toLowerCase();
        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numeroDeTemporadas(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Serie não encontrada ");
        }


    }

    private void listarSeriesBuscadas(){
        series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma serie pelo nome: ");
        var nomeSerie = leitura.nextLine().toLowerCase();
        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()){
            System.out.println("Dados ds série" + serieBuscada.get());
        }else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriePorAtor(){
        System.out.println("Qual o nome para busca? ");
        var nomeAtor = leitura.nextLine().toLowerCase();
        List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("Séries em que "+ nomeAtor +" trabalhou: " );
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " avaliação: "+ s.getAvaliacao()));
    }

    private void buscarSeriesPorAvaliacao(){
        System.out.println("Digite a avaliação minima da série que deseja pesquisar: ");
        String entrada = leitura.nextLine().replace(",",".");
        try {
            double avaliacaoDigitada = Double.parseDouble(entrada);
            List<Serie> avaliacoesEncontradas = serieRepository.findByAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(avaliacaoDigitada);
            System.out.println("Series com Avaliação apartir de :" +avaliacaoDigitada);
            avaliacoesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " avaliação: "+ s.getAvaliacao()));
        }catch (NumberFormatException e){
            System.out.println("Valor inválido. Por favor, digite um número válido (ex: 8.5).");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Digite a categoria/gênero desejada para buscar: ");
        var nomeCategoria = leitura.nextLine().toLowerCase();
        Categoria genero = Categoria.fromStringPortuges(nomeCategoria);
        List<Serie> seriesPorCAtegoria = serieRepository.findByGenero(genero);
        System.out.println("Series da categoria/gênero: "+ nomeCategoria);
        seriesPorCAtegoria.forEach(System.out::println);
    }

    private void buscarSeriesPorTemporadasEAvaliacao() {
        try {
            System.out.println("Digite o número exato de temporadas desejado: ");
            int temporadas = Integer.parseInt(leitura.nextLine());

            System.out.println("Digite a avaliação mínima desejada (ex: 7.5)");
            double avaliacao = Double.parseDouble(leitura.nextLine().replace(",", "."));

            List<Serie> series = serieRepository.seriesPorTemporadasEAvaliacao(temporadas,avaliacao);

            if (series.isEmpty()){
                System.out.println("Nenuma série encontrada com "+ temporadas + "teporada(s)");
            }else {
                System.out.println("Séries encontradas: ");
                series.forEach(s -> System.out.println(s.getTitulo() +"- Temporadas: "+s.getTotalTemporadas() + " - Avaliação: " + s.getAvaliacao()));
            }
        }catch (NumberFormatException e){
            System.out.println("Entrada inválida. Certifique-se de digitar corretamente ");
        }
    }

}
