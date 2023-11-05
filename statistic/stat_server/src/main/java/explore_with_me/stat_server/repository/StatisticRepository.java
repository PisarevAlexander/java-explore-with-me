package explore_with_me.stat_server.repository;

import explore_with_me.stat_server.stat.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import explore_with_me.stat_server.stat.StatHitsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Statistic repository
 */

@Repository
public interface StatisticRepository extends JpaRepository<Stat, Long> {

    /**
     * Find all by uri and unique ip list.
     * @param start the start
     * @param end   the end
     * @param uri   the uri
     * @return the list
     */

    @Query("SELECT new explore_with_me.stat_server.stat.StatHitsDto(s.app, s.uri, count(distinct s.ip)) " +
            "FROM Stat as s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "AND s.uri IN ?3 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) desc")
    List<StatHitsDto> findAllByUriAndUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uri);

    /**
     * Find all by uri and not unique ip list.
     * @param start the start
     * @param end   the end
     * @param uri   the uri
     * @return the list
     */

    @Query("SELECT new explore_with_me.stat_server.stat.StatHitsDto(s.app, s.uri, count(s.ip)) " +
            "FROM Stat as s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "AND s.uri IN ?3 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(s.ip) desc")
    List<StatHitsDto> findAllByUriAndNotUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uri);

    /**
     * Find all by unique ip list.
     * @param start the start
     * @param end   the end
     * @return the list
     */

    @Query("SELECT new explore_with_me.stat_server.stat.StatHitsDto(s.app, s.uri, count(distinct s.ip)) " +
            "FROM Stat as s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) desc")
    List<StatHitsDto> findAllByUniqueIp(LocalDateTime start, LocalDateTime end);

    /**
     * Find all by not unique ip list.
     * @param start the start
     * @param end   the end
     * @return the list
     */

    @Query("SELECT new explore_with_me.stat_server.stat.StatHitsDto(s.app, s.uri, count(s.ip)) " +
            "FROM Stat as s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(s.ip) desc")
    List<StatHitsDto> findAllByNotUniqueIp(LocalDateTime start, LocalDateTime end);


    /**
     * Count distinct by uri list.
     * @param s the s
     * @return the list
     */

    @Query("SELECT new explore_with_me.stat_server.stat.StatHitsDto(s.app, s.uri, count(distinct s.ip)) " +
            "FROM Stat as s " +
            "WHERE s.uri = ?1 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip)")
    List<StatHitsDto> countDistinctByUri(String s);
}