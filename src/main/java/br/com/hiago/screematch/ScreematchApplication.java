package br.com.hiago.screematch;

import br.com.hiago.screematch.model.DadosEpisodios;
import br.com.hiago.screematch.model.DadosSerie;
import br.com.hiago.screematch.service.ConsumoApi;
import br.com.hiago.screematch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreematchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreematchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=lost&apikey=74979642");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
//		System.out.println(dadosSerie);

		json = consumoApi.obterDados("http://www.omdbapi.com/?t=lost&season=1&episode=2&apikey=74979642");
		DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEpisodios);
	}

}
