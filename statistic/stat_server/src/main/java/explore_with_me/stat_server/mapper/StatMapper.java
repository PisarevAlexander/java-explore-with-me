package explore_with_me.stat_server.mapper;

import explore_with_me.stat_dto.StatDto;
import explore_with_me.stat_server.stat.Stat;

/**
 * Stat mapper
 */

public class StatMapper {

    /**
     * Stat DTO to stat
     * @param statDto the stat dto
     * @return the stat
     */

    public static Stat toStat(StatDto statDto) {
        Stat stat = new Stat();
        stat.setApp(statDto.getApp());
        stat.setUri(statDto.getUri());
        stat.setIp(statDto.getIp());
        stat.setTimestamp(statDto.getTimestamp());
        return stat;
    }
}