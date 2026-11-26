package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Marca;
import com.mycompany.datastock.service.dto.MarcaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Marca} and its DTO {@link MarcaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MarcaMapper extends EntityMapper<MarcaDTO, Marca> {}
