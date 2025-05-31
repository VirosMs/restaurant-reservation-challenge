package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/* * Mapper interface for converting between RestaurantTables entity and TablesDTO.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface TablesMapper {

    /**
     * Converts a TablesDTO to a RestaurantTables entity.
     *
     * @param dto the TablesDTO to convert
     * @return a RestaurantTables entity
     */
    RestaurantTables toEntity(TablesDTO dto);

    /**
     * Converts a RestaurantTables entity to a TablesDTO.
     *
     * @param entity the RestaurantTables entity to convert
     * @return a TablesDTO
     */
    TablesDTO toDto(RestaurantTables entity);

    /**
     * Updates an existing RestaurantTables entity with values from a TablesDTO.
     *
     * @param dto the TablesDTO containing updated values
     * @param entity the RestaurantTables entity to update
     */
    void updateEntityFromDto(TablesDTO dto, @MappingTarget RestaurantTables entity);

}
