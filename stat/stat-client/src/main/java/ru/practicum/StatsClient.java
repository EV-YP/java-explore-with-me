package ru.practicum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsClient {

    private final String serverUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public StatsClient(@Value(value = "${stat-server.url:http://stat-server:9090}") String serverUrl,
                       RestTemplateBuilder builder) {
        this.serverUrl = serverUrl;
        this.restTemplate = builder.build();
    }

    public ResponseEntity<Object> createHit(HttpServletRequest request, String appName) {
        String uri = request.getRequestURI();
        EndpointHitDto Dto = getEndpointHitDto(request, appName);
        return post(serverUrl + "/hit", Dto);

    }

    public List<ViewStatsDto> viewStats(LocalDateTime start, LocalDateTime end,
                                        List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StatsRequestDto.DATE_FORMAT);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start.format(formatter));
        params.put("end", end.format(formatter));
        params.put("uris", uris != null ? String.join(",", uris) : "");
        params.put("unique", unique != null ? unique : false);

        ResponseEntity<Object> obj = get(serverUrl + "/stats", params);

        ObjectMapper mapper = new ObjectMapper();
        List<ViewStatsDto> views = null;

        if (obj.getStatusCode().is2xxSuccessful() && obj.getBody() != null) {
            try {
                views = mapper.readValue(
                        mapper.writeValueAsString(obj.getBody()), new TypeReference<>() {
                        });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return views;
    }

    private ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    private ResponseEntity<Object> post(String path, Object body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters,
                                                          @Nullable T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(path);
        if (parameters != null) {
            parameters.forEach(builder::queryParam);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(builder.build().encode().toUri(),
                method, entity, Object.class);
    }

    private EndpointHitDto getEndpointHitDto(HttpServletRequest request, String appName) {
        return EndpointHitDto.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
