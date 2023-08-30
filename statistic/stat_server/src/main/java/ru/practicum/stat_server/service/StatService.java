package ru.practicum.stat_server.service;

import ru.practicum.stat_dto.StatDto;
import ru.practicum.stat_server.stat.Stat;
import ru.practicum.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    Stat create(StatDto statDto);

    List<StatHitsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
