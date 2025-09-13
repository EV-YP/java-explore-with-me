package ru.practicum.event.mapper;

import org.mapstruct.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(NewEventDto event);

    EventShortDto toEventShortDto(Event event);

    EventFullDto toEventFullDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    Event toEvent(UpdateEventAdminRequest request, @MappingTarget Event event);

    EventCommentsDto toEventCommentsDto(Event event);
}
