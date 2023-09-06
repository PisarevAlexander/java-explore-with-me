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
import java.util.Objects;

@Service
@Slf4j
public class StatClient extends BaseClient {

    @Autowired
    public StatClient(@Value("${stats-client.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public void createStat(HttpServletRequest request) {
        StatDto statDto = new StatDto();
        statDto.setIp(request.getRemoteAddr());
        statDto.setUri(request.getRequestURI());
        statDto.setTimestamp(LocalDateTime.now());
        statDto.setApp("ewm_service");
        log.info("Get request to stat server ");
        post("/hit", statDto);
    }

    public Long getView(Long eventId) {
        String responseBody = Objects.requireNonNull(get("/view/" + eventId).getBody()).toString();
        return Long.parseLong(responseBody);
    }
}
