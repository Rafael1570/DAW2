package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.*;
import java.util.List;

/** Conversiones manuales; las relaciones necesarias se cargan en los DAO. */
public final class ProvinceMapper {
    private ProvinceMapper() {}

    public static ProvinceDTO toDTO(Province entity) {
        return new ProvinceDTO(entity.getId(), entity.getCode(), entity.getName(), entity.getRegion().getName());
    }

    public static List<ProvinceDTO> toDTOList(List<Province> entities) {
        return entities.stream().map(ProvinceMapper::toDTO).toList();
    }

    public static ProvinceDetailDTO toDetailDTO(Province entity) {
        return new ProvinceDetailDTO(entity.getId(), entity.getCode(), entity.getName(), RegionMapper.toDTO(entity.getRegion()));
    }

    public static ProvinceUpdateDTO toUpdateDTO(Province entity) {
        return new ProvinceUpdateDTO(entity.getId(), entity.getCode(), entity.getName(), entity.getRegion().getId());
    }

    public static Province toEntity(ProvinceCreateDTO dto, Region region) {
        return new Province(dto.getCode(), dto.getName(), region);
    }

    public static Province toEntity(ProvinceUpdateDTO dto, Region region) {
        Province entity = new Province(dto.getCode(), dto.getName(), region);
        entity.setId(dto.getId());
        return entity;
    }
}
