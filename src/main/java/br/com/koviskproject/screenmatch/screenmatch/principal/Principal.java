package br.com.koviskproject.screenmatch.screenmatch.principal;

import br.com.koviskproject.screenmatch.screenmatch.models.DadosEpisodio;
import br.com.koviskproject.screenmatch.screenmatch.models.DadosSerie;
import br.com.koviskproject.screenmatch.screenmatch.models.DadosTemporada;
import br.com.koviskproject.screenmatch.screenmatch.services.ConsumoApi;
import br.com.koviskproject.screenmatch.screenmatch.services.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    //https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "27ce6fd6";

    public void exibeMenu(){
        System.out.println("Informe a serie para buscar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(URL + nomeSerie + "&apikey=" + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporada = new ArrayList<>();
        for (int i = 1; i <= dados.totalTemporada(); i++) {
            json = consumo.obterDados(URL + nomeSerie + "&season="+i+"&apikey=" + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporada.add(dadosTemporada);
        }
        temporada.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporada(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporada.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporada.forEach( t -> t.episodios().forEach(e -> System.out.println(e.titulo())) );
    }
}
