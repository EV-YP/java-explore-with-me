package ru.practicum.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.practicum.model.EndpointHit;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HitsObjectMapper implements RowMapper<EndpointHit> {
    @Override
    public EndpointHit mapRow(ResultSet rs, int rowNum) throws SQLException {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(rs.getString("app"));
        endpointHit.setId(rs.getLong("id"));
        endpointHit.setIp(rs.getString("ip"));
        endpointHit.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        endpointHit.setUri(rs.getString("uri"));

        return endpointHit;
    }
}
