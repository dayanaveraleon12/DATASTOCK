package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Presentacion;
import com.mycompany.datastock.service.dto.PresentacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Presentacion} and its DTO {@link PresentacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PresentacionMapper extends EntityMapper<PresentacionDTO, Presentacion> {}
