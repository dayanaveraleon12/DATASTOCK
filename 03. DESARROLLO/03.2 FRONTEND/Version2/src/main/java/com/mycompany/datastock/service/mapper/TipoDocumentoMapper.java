package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.TipoDocumento;
import com.mycompany.datastock.service.dto.TipoDocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDocumento} and its DTO {@link TipoDocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper extends EntityMapper<TipoDocumentoDTO, TipoDocumento> {}
