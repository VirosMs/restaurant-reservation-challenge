package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.Tables.Tables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TablesMapper {


    Tables toEntity(TablesDTO dto);

    TablesDTO toDto(Tables entity);

    void updateEntityFromDto(TablesDTO dto, @MappingTarget Tables entity);

}
