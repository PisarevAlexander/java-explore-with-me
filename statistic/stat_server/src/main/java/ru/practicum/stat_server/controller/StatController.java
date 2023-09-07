package ru.practicum.stat_server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat_dto.StatDto;
import ru.practicum.stat_server.service.StatService;
import ru.practicum.stat_server.stat.Stat;
import ru.practicum.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class StatController {

    private final StatService statService;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostMapping("/hit")
    public Stat createStat(@RequestBody StatDto statDto) {
        log.info("Post statistic request {}", statDto);
        return statService.create(statDto);
    }

    @GetMapping("/stats")
    public List<StatHitsDto> getStats(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                      @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                      @RequestParam(required = false) List<String> uris, @RequestParam Boolean unique) {
        log.info("Get statistic request");
        return statService.getStat(start, end, uris, unique);
    }

    @GetMapping("/view/{eventId}")
    public Long getView(@PathVariable long eventId) {
        return statService.getView(eventId);
    }
}
