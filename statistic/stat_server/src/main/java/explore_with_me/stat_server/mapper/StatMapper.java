package explore_with_me.stat_server.mapper;

import explore_with_me.stat_dto.StatDto;
import explore_with_me.stat_server.stat.Stat;
import explore_with_me.stat_server.stat.StatHitsDto;

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
