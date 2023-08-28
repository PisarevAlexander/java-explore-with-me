package ru.practicum.stat_client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stat_client.exception.BadRequestException;
import ru.practicum.stat_dto.StatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatClient extends BaseClient {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createStat(StatDto statDto) {
        return post("/hit", statDto);
    }

    public ResponseEntity<Object> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new BadRequestException("Время начала не может быть позже окончания");
        }

        Map<String, Object> parameters;

        if (uris == null) {
            parameters = Map.of(
                    "start", start.format(DATE_TIME_FORMATTER),
                    "end", end.format(DATE_TIME_FORMATTER),
                    "unique", unique
            );
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        } else {
            parameters = Map.of(
                    "start", start.format(DATE_TIME_FORMATTER),
                    "end", end.format(DATE_TIME_FORMATTER),
                    "uris", String.join(",", uris),
                    "unique", unique
            );
        }
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}
