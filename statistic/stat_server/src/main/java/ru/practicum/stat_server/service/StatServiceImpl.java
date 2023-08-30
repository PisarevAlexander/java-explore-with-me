package ru.practicum.stat_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat_dto.StatDto;
import ru.practicum.stat_server.mapper.StatMapper;
import ru.practicum.stat_server.repository.StatRepository;
import ru.practicum.stat_server.stat.Stat;
import ru.practicum.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatServiceImpl implements StatService {

    public final StatRepository repository;

    @Override
    @Transactional
    public Stat create(StatDto statDto) {
        return repository.save(StatMapper.toStat(statDto));
    }

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
}
