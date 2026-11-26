package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.EstadoProducto;
import com.mycompany.datastock.service.dto.EstadoProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoProducto} and its DTO {@link EstadoProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadoProductoMapper extends EntityMapper<EstadoProductoDTO, EstadoProducto> {}
