package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {

  public static void main(String[] args) throws IOException, InterruptedException {
    try {
      Scanner leitura = new Scanner(System.in);
      System.out.println("Digite um filme para busca: ");
      var busca = leitura.nextLine();
      String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=4f274003";

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endereco))
          .build();
//    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//        .thenApply(HttpResponse::body)
//        .thenAccept(System.out::println)
//        .exceptionally(ex -> {
//          System.err.println("Erro ao realizar a requisição: " + ex.getMessage());
//          return null;
//        })
//        .join();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      String json = response.body();
      System.out.println(json);

    /*
    Vai fazer com que as propriedades do meu record:
      public record TituloOmdb(String title, String year, String runtime) {}
        que hoje estão como lowerCase ex: title, fique como Title (UPPER_CAMEL_CASE).

    Permitindo assim que meu conversor de json para classe de match no nome das props,
    ou seja, meu json vem como "Title" e meu TituloOmdb apesar de title foi convertido via builder
    para Title também, permitindo que ele sette as coisas que vem do json para o Record
      ex json {"Title: "Contadores de Carros", "Year": 1995 }
      gson.fromJson(json, TituloOmdb.class);
    * */
      Gson gson = new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
          .create();

//    Titulo meuTitulo = gson.fromJson(json, Titulo.class);
      TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
      System.out.println("meuTituloOmdb: " + meuTituloOmdb);

      Titulo meuTitulo = new Titulo(meuTituloOmdb);
      System.out.println("meuTitulo: " + meuTitulo);
    } catch (NumberFormatException e) {
      System.out.println("Aconteceu um erro: ");
      System.out.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println("Algum erro de argumento na busca, verifique o endereço.");
    } catch (Exception e) {
      System.out.println("Erro inesperado código 56789");
    } finally {
      System.out.println("Executa independente de sucesso ou erro.");
    }

  }
}
