package ru.practicum.request.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created")
    private final LocalDateTime created = LocalDateTime.now();

    @OneToOne
    private Event event;

    @OneToOne(fetch = FetchType.EAGER)
    private User requester;

    @Column(name = "state_id")
    @Enumerated(EnumType.ORDINAL)
    private State status;
}
