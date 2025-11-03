package br.com.alura.screenmatch.principal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {

  public static void main(String[] args) {

    Scanner leitura = new Scanner(System.in);
    System.out.println("Digite um filme para busca: ");
    var busca = leitura.nextLine();
    String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=4f274003";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build();
    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println)
        .exceptionally(ex -> {
          System.err.println("Erro ao realizar a requisição: " + ex.getMessage());
          return null;
        })
        .join();
  }
}
