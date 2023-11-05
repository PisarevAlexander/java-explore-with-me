package explore_with_me.stat_server.service;

import explore_with_me.stat_dto.StatDto;
import explore_with_me.stat_server.mapper.StatMapper;
import explore_with_me.stat_server.repository.StatisticRepository;
import explore_with_me.stat_server.stat.Stat;
import explore_with_me.stat_server.stat.StatHitsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * Stat service
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StatServiceImpl implements StatService {

    public final StatisticRepository repository;

    /**
     * Create stat
     * @param statDto the stat dto
     * @return the stat
     */

    @Override
    @Transactional
    public Stat create(StatDto statDto) {
        return repository.save(StatMapper.toStat(statDto));
    }

    /**
     * Get stat
     * @param start  the start
     * @param end    the end
     * @param uris   the uris
     * @param unique the unique
     * @return the stat
     */

    @Override
    @Transactional
    public List<StatHitsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<StatHitsDto> statHitsDtos;

        if (uris != null) {
            if (unique) {
                statHitsDtos = repository.findAllByUriAndUniqueIp(start, end, uris);
            } else {
                statHitsDtos = repository.findAllByUriAndNotUniqueIp(start, end, uris);
            }
        } else {
            if (unique) {
                statHitsDtos = repository.findAllByUniqueIp(start, end);
            } else {
                statHitsDtos = repository.findAllByNotUniqueIp(start, end);
            }
        }
        return statHitsDtos;
    }

    /**
     * Get views
     * @param eventId the event id
     * @return the views
     */

    @Override
    @Transactional
    public Long getView(long eventId) {
        List<StatHitsDto> views = repository.countDistinctByUri("/events/" + eventId);
        Long view = Long.valueOf(views.size());
        log.info("Get views={}", view);
        return Objects.requireNonNullElse(view, 0L);
    }
}