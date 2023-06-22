package org.example;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(">>> Initiating request");

        String method = "";
        String auth = "";
        String url = "";
        String query = "";
        String headersArg = "";

        for (int a = 0; a < args.length; a++) {
            if (args[a].equalsIgnoreCase("--method")) {
                method = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--url")) {
                url = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--auth")) {
                auth = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--headers")
                    || args[a].equalsIgnoreCase("-H")) {
                headersArg = args[a + 1];
            }
        }

        Arrays.stream(headersArg.split(",")).forEach(h -> {

        });


        // client
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        switch (method) {
            case "GET":
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .headers(headers)
                        .build();
                try {
                    client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
        }


    }
}