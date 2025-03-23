package com.example.codechecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class CodeCheckerService {

    @Autowired(required = false)
    private WebClient webClient;

    private static final String MODEL_NAME = "gemini-2.0-flash";
    private static final String API_KEY = "";

    // One-shot analysis method using a Map to build JSON payload
    public Mono<String> analyzeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Mono.just("No code provided!");
        }
        if (webClient == null) {
            return Mono.just("WebClient not configured. Cannot call Gemini API.");
        }

        // Build the request body as a Map, letting Spring serialize it to JSON
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", "Debug and analyze this code snippet:\n" + code + "\nProvide suggestions for improvement.")
                        ))
                )
        );

        return webClient.post()
                .uri("/models/" + MODEL_NAME + ":generateContent?key=" + API_KEY)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // Parse the response (naively) to extract text or error
                    if (response.contains("\"error\":")) {
                        return "Error in API response: " + response;
                    } else if (response.contains("\"text\":")) {
                        String[] split = response.split("\"text\":");
                        if (split.length > 1) {
                            String textPart = split[1].split("}")[0].trim();
                            textPart = textPart.replaceAll("^\"|\"$", "");
                            return textPart;
                        }
                        return "Error: Could not parse 'text' field in API response.";
                    } else {
                        return "Error: No 'text' field in API response - " + response;
                    }
                })
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.just("Error calling Gemini API: " + e.getStatusCode() + " " + e.getResponseBodyAsString())
                )
                .onErrorResume(e ->
                        Mono.just("Error: Unexpected failure - " + e.getMessage())
                );
    }

    /**
     * Real-time analysis stream using SSE.
     * For demonstration, this method simulates processing by splitting the code into lines.
     */
    public Flux<ServerSentEvent<String>> analyzeCodeStream(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Flux.just(ServerSentEvent.builder("No code provided!").build());
        }

        String[] lines = code.split("\n");

        // Process each line with a delay, and check for errors using a naive approach.
        return Flux.fromArray(lines)
                .delayElements(Duration.ofSeconds(1))
                .flatMap(this::checkLine)
                .concatWith(Flux.just(
                        ServerSentEvent.<String>builder()
                                .event("complete")
                                .data("Analysis complete!")
                                .build()
                ));
    }

    /**
     * Naively checks a single line for the keyword "error".
     * If found, emits both an "error" event and a "fix" event; otherwise, emits a "line" event.
     */
    private Flux<ServerSentEvent<String>> checkLine(String line) {
        if (line.toLowerCase().contains("error")) {
            return Flux.just(
                    ServerSentEvent.<String>builder()
                            .event("error")
                            .data("Error found in line: " + line)
                            .build(),
                    ServerSentEvent.<String>builder()
                            .event("fix")
                            .data("Suggested fix: remove or handle 'error' in this line.")
                            .build()
            );
        } else {
            return Flux.just(
                    ServerSentEvent.<String>builder()
                            .event("line")
                            .data("Analyzing line: " + line)
                            .build()
            );
        }
    }
}
