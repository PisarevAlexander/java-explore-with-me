package ru.practicum.stat_client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat_client.client.StatClient;
import ru.practicum.stat_dto.StatDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {

    private final StatClient statClient;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostMapping("/hit")
    public ResponseEntity<Object> createStat(@Valid @RequestBody StatDto statDto) {
        log.info("Post statistic request");
        return statClient.createStat(statDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @NotNull @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                           @RequestParam @NotNull @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Get statistic request");
        return statClient.getStat(start, end, uris, unique);
    }
}