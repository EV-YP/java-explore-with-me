package ru.practicum.event.model;

public enum State {
    PUBLISHED,
    CONFIRMED,
    REJECTED,
    PENDING,
    PUBLISH_EVENT,
    REJECT_EVENT,
    CANCEL_REVIEW,
    SEND_TO_REVIEW,
    CANCELED
}