package ru.practicum.stat_server.mapper;

import ru.practicum.stat_dto.StatDto;
import ru.practicum.stat_server.stat.Stat;
import ru.practicum.stat_server.stat.StatHitsDto;

public class StatMapper {

    public static Stat toStat(StatDto statDto) {
        Stat stat = new Stat();
        stat.setApp(statDto.getApp());
        stat.setUri(statDto.getUri());
        stat.setIp(statDto.getIp());
        stat.setTimestamp(statDto.getTimestamp());
        return stat;
    }

    public static StatHitsDto statHitsDto(Stat stat) {
        StatHitsDto statHitsDto = new StatHitsDto();
        statHitsDto.setApp(stat.getApp());
        statHitsDto.setUri(stat.getUri());
        return statHitsDto;
    }
}
