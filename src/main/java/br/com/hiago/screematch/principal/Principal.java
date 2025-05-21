package br.com.hiago.screematch.principal;

import br.com.hiago.screematch.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=74979642";

    public void exibeMenu(){
        System.out.println("Digite o nome de série que deseja buscar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(
                ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }

}
