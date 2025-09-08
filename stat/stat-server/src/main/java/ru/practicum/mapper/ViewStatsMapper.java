package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.ViewStats;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ViewStatsMapper {

    ViewStats mapViewStatsToDto(ViewStatsDto viewStatsDto);

    ViewStatsDto mapDtoToViewStats(ViewStats viewStats);
}
