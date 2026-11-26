package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.MotivoSalida;
import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MotivoSalida} and its DTO {@link MotivoSalidaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MotivoSalidaMapper extends EntityMapper<MotivoSalidaDTO, MotivoSalida> {}
