package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.*;
import java.util.List;

/** Conversiones manuales; las relaciones necesarias se cargan en los DAO. */
public final class RegionMapper {
    private RegionMapper() {}

    public static RegionDTO toDTO(Region entity) {
        return new RegionDTO(entity.getId(), entity.getCode(), entity.getName());
    }

    public static List<RegionDTO> toDTOList(List<Region> entities) {
        return entities.stream().map(RegionMapper::toDTO).toList();
    }

    public static RegionDetailDTO toDetailDTO(Region entity) {
        return new RegionDetailDTO(entity.getId(), entity.getCode(), entity.getName(), ProvinceMapper.toDTOList(entity.getProvinces()));
    }

    public static RegionUpdateDTO toUpdateDTO(Region entity) {
        return new RegionUpdateDTO(entity.getId(), entity.getCode(), entity.getName());
    }

    public static Region toEntity(RegionCreateDTO dto) {
        return new Region(dto.getCode(), dto.getName());
    }

    public static Region toEntity(RegionUpdateDTO dto) {
        Region entity = new Region(dto.getCode(), dto.getName());
        entity.setId(dto.getId());
        return entity;
    }
}
