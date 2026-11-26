package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.service.dto.SitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sitio} and its DTO {@link SitioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SitioMapper extends EntityMapper<SitioDTO, Sitio> {}
