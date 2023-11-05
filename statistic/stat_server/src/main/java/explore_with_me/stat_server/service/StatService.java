package explore_with_me.stat_server.service;

import explore_with_me.stat_dto.StatDto;
import explore_with_me.stat_server.stat.Stat;
import explore_with_me.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Stat service
 */

public interface StatService {

    /**
     * Create stat
     * @param statDto the stat dto
     * @return the stat
     */

    Stat create(StatDto statDto);

    /**
     * Get stat
     * @param start  the start
     * @param end    the end
     * @param uris   the uris
     * @param unique the unique
     * @return the stat
     */

    List<StatHitsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    /**
     * Get views
     * @param eventId the event id
     * @return the views
     */

    Long getView(long eventId);
}