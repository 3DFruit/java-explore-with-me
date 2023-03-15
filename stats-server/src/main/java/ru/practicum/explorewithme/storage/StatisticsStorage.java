package ru.practicum.explorewithme.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsStorage extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicum.explorewithme.model.ViewStats(eh.app, eh.uri, count(eh.id)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp > ?1 and eh.timestamp < ?2 and eh.uri in ?3 " +
            "group by eh.app, eh.uri " +
            "order by count(eh.id) desc")
    List<ViewStats> getStatisticOfUrisByStartAndEnd(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query("select new ru.practicum.explorewithme.model.ViewStats(eh.app, eh.uri, count(distinct(eh.ip))) " +
            "from EndpointHit as eh " +
            "where eh.timestamp > ?1 and eh.timestamp < ?2 and eh.uri in ?3 " +
            "group by eh.app, eh.uri " +
            "order by count(eh.id) desc")
    List<ViewStats> getStatisticOfUrisByStartAndEndUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
