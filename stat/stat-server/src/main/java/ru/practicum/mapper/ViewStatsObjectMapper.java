package ru.practicum.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.practicum.model.ViewStats;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ViewStatsObjectMapper implements RowMapper<ViewStats> {
    @Override
    public ViewStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        ViewStats viewStats = new ViewStats();
        viewStats.setApp(rs.getString("app"));
        viewStats.setUri(rs.getString("uri"));
        return viewStats;
    }
}
