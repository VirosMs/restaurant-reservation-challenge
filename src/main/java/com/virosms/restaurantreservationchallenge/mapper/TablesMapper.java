package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TablesMapper {


    RestaurantTables toEntity(TablesDTO dto);

    TablesDTO toDto(RestaurantTables entity);

    void updateEntityFromDto(TablesDTO dto, @MappingTarget RestaurantTables entity);

}
