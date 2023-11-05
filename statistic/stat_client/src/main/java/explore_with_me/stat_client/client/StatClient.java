package explore_with_me.stat_client.client;

import explore_with_me.stat_client.exception.BadRequestException;
import explore_with_me.stat_dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Stat client
 */

@Service
public class StatClient extends BaseClient {

    /**
     * The constant DATE_TIME_FORMATTER
     */

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Instantiates a new Stat client.
     * @param serverUrl the server url
     * @param builder   the builder
     */

    @Autowired
    public StatClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * Create stat response entity.
     * @param statDto the stat dto
     * @return the response entity
     */

    public ResponseEntity<Object> createStat(StatDto statDto) {
        return post("/hit", statDto);
    }

    /**
     * Get stat
     * @param start  the start
     * @param end    the end
     * @param uris   the uris
     * @param unique the unique
     * @return the stat
     */

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

    /**
     * Find view response entity
     * @param eventId the event id
     * @return the response entity
     */

    public ResponseEntity<Object> findView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        return get("/view/{eventId}", parameters);
    }
}