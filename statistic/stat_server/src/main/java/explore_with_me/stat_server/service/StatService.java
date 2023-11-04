package explore_with_me.stat_server.service;

import explore_with_me.stat_dto.StatDto;
import explore_with_me.stat_server.stat.Stat;
import explore_with_me.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    Stat create(StatDto statDto);

    List<StatHitsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    Long getView(long eventId);
}
