package ru.practicum.explorewithme.model.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "id", expression = "java(null)")
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
