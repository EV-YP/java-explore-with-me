package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.State;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findRequestById(Integer requestId);

    List<Request> findRequestsByRequesterId(Integer requesterId);

    List<Request> findRequestsByEventIdAndEventInitiatorId(Integer eventId, Integer eventInitiatorId);

    List<Request> findRequestsByEventIdAndStatus(Integer eventId, State status);

    Request findRequestsByEventIdAndRequesterId(Integer eventId, Integer requesterId);
}
