package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatisticsClient {
    @Value("${stats-server.url:http://localhost:9090}")
    private static String SERVER_URL;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final WebClient client;

    public StatisticsClient() {
        this.client = WebClient.builder()
                .baseUrl(SERVER_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<EndpointHitDto> writeStat(String app, String uri, String ip, LocalDateTime timestamp) {
        return client
                .post()
                .uri("/hit")
                .bodyValue(new EndpointHitDto(null, app, uri, ip, timestamp))
                .retrieve()
                .bodyToMono(EndpointHitDto.class);
    }

    public Mono<List<ViewStats>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String startString = start.format(DATE_TIME_FORMATTER);
        String endString = end.format(DATE_TIME_FORMATTER);
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", startString)
                        .queryParam("end", endString)
                        .queryParam("unique", unique)
                        .queryParam("uris", uris)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
