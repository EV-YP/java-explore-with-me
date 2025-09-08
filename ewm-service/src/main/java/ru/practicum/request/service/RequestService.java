package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto addRequest(Integer userId, Integer eventId);

    RequestDto cancelRequest(Integer userId, Integer requestId);

    List<RequestDto> getRequests(Integer userId);
}
