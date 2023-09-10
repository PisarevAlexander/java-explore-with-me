package ru.practicum.main_server.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class StatServerClient extends BaseServerClient {

    @Autowired
    public StatServerClient(@Value("${stats-client.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public void createStat(HttpServletRequest request) {
        StatServerDto statDto = new StatServerDto();
        statDto.setIp(request.getRemoteAddr());
        statDto.setUri(request.getRequestURI());
        statDto.setTimestamp(LocalDateTime.now());
        statDto.setApp("ewm_service");
        log.info("POST request to stat client");
        post("/hit", statDto);
    }

    public Long getView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
            String responseBody = (Objects.requireNonNullElse(get("/view/{eventId}", parameters).getBody(), 0L)).toString();
            return Long.parseLong(responseBody);
    }
}
