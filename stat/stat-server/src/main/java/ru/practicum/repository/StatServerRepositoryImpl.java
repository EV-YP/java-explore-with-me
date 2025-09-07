package ru.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.mapper.ViewStatsObjectMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatServerRepositoryImpl implements StatServerRepository {

    private final JdbcTemplate jdbc;
    private final ViewStatsObjectMapper viewStatsMapper = new ViewStatsObjectMapper();

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end) {
        List<ViewStats> viewStatsList = jdbc.query("SELECT app, uri FROM hits " +
                "WHERE timestamp BETWEEN ? AND ? GROUP BY hits.app, hits.uri " +
                "ORDER BY COUNT(*) DESC", new ViewStatsObjectMapper(), start, end);

        for (ViewStats viewStats : viewStatsList) {
            int hits = jdbc.queryForObject("SELECT COUNT(uri) FROM hits " +
                    "WHERE uri = ?", Integer.class, viewStats.getUri());
            viewStats.setHits(hits);
        }
        return viewStatsList;
    }

    @Override
    public List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris) {
        List<ViewStats> viewStatsList = jdbc.query("SELECT app, uri FROM hits " +
                "WHERE uri = ? AND timestamp BETWEEN ? AND ? GROUP BY hits.app, hits.uri " +
                "ORDER BY COUNT(*) DESC", new ViewStatsObjectMapper(), uris.getFirst(), start, end);

        for (ViewStats viewStats : viewStatsList) {
            int hits = jdbc.queryForObject("SELECT COUNT(DISTINCT ip) FROM hits " +
                    "WHERE uri = ?", Integer.class, viewStats.getUri());
            viewStats.setHits(hits);
        }
        return viewStatsList;
    }

    @Override
    public void saveHit(EndpointHit hit) {
        jdbc.update("INSERT INTO hits (app, uri, ip, timestamp) VALUES (?, ?, ?, ?)",
                hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp());
    }
}
