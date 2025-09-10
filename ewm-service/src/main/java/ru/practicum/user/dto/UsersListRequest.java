package ru.practicum.user.dto;

import java.util.List;

public record UsersListRequest(
        List<Integer> ids,
        Integer from,
        Integer size) {
}
