package com.andmal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final String[] methodArgs = {"-M", "--method"};
    private static String[] pathArgs = new String[0];
    public static void main(String[] args) {
        String method = "";
        String auth = "";
        String url = "";
        String query = "";
        String headersArg = "";
        String body = "";

        if (args.length < 2) {
            System.out.println("Please provide arguments!");
            return;
        }

        for (int a = 0; a < args.length; a++) {
            var index = new AtomicInteger(a);
            if (Arrays.stream(methodArgs).anyMatch(ar -> ar.equals(args[index.get()]))) {
                method = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--url")) {
                url = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--auth") || args[a].equalsIgnoreCase("-A")) {
                auth = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--body")) {
                body = args[a + 1];
            } else if (args[a].equalsIgnoreCase("--headers") || args[a].equalsIgnoreCase("-H")) {
                headersArg = args[a + 1];
            }
        }

        // client
        try (HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build()) {

            HttpRequest.Builder reqBuilder = HttpRequest.newBuilder();

            Map<String, String> headersMap = new HashMap<>();

            if (Arrays.asList(args).contains("--headers")) {
                if (!headersArg.isEmpty()) {
                    Arrays.stream(headersArg.split(",")).forEach(h -> {
                        String[] headElements = h.split(":");
                        headersMap.put(headElements[0].trim(), headElements[1].trim());
                    });
                    headersMap.entrySet().forEach((e) -> {
                        reqBuilder.header(e.getKey(), e.getValue());
                    });
                }
            }

            if (Arrays.asList(args).contains("--auth") || Arrays.asList(args).contains("-A")) {
                auth = auth.replace("'", "").replace("\"", "");
                var TOKEN = Base64.getEncoder().encodeToString(
                        String.format("%s:%s", auth.split(":")[0], auth.split(":")[1]).getBytes());
                reqBuilder.header("Authorization", "Basic " + TOKEN);
            }

            System.out.println("[ \uD83D\uDE3A JCURL \uD83D\uDE3A ] Initiating request");

            switch (method) {
                case "GET":
                    try {
                        HttpRequest request = reqBuilder.GET().uri(URI.create(url)).build();
                        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                        response.thenApply(resp -> {
                            System.out.println("[ \uD83D\uDE3B ] \u001b[33mheaders:\u001b[0m");
                            resp.headers().map().forEach((key, value) -> System.out.printf("%s : %s \r\n", key, value));
                            System.out.println(resp.body());
                            return "";
                        }).get();
                    } catch (Exception e) {
                        System.out.println("\uD83D\uDE3F \uD83D\uDE3F \uD83D\uDE3F. " + e.getMessage());
                    }
                    break;
                case "POST":
                    HttpRequest request = reqBuilder
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .uri(URI.create(url))
                            .build();
                    try {
                        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
                        resp.headers().map().forEach((key, value) -> System.out.printf("%s : %s \r\n", key, value));
                        System.out.println(resp.body());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "PUT":
                    HttpRequest put = reqBuilder
                            .PUT(HttpRequest.BodyPublishers.ofString(body))
                            .uri(URI.create(url))
                            .build();
                    try {
                        HttpResponse<String> resp = client.send(put, HttpResponse.BodyHandlers.ofString());
                        resp.headers().map().forEach((key, value) ->
                                System.out.printf("\u001b[32m %s\u001b[0m : \u001b[33m%s\u001b[0m \r\n", key, value));
                        System.out.println("\n");
                        System.out.println(resp.body());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }
        }


    }
}