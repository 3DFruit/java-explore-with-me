package ru.practicum.explorewithme.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.storage.StatisticsStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    StatisticsStorage statisticsStorage;

    @Autowired
    public StatisticsServiceImpl(StatisticsStorage statisticsStorage) {
        this.statisticsStorage = statisticsStorage;
    }

    @Override
    @Transactional
    public EndpointHitDto saveStat(EndpointHitDto dto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(
                statisticsStorage.save(mapper.convertValue(dto, EndpointHit.class)),
                EndpointHitDto.class);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            return unique ? statisticsStorage.getAllStatisticsByStartAndEndUniqueIp(start, end)
                    : statisticsStorage.getAllStatisticsByStartAndEnd(start, end);
        }
        return unique ? statisticsStorage.getStatisticOfUrisByStartAndEndUniqueIp(start, end, uris)
                : statisticsStorage.getStatisticOfUrisByStartAndEnd(start, end, uris);
    }
}
