package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.HistorialError;
import com.mycompany.datastock.service.dto.CuentaDTO;
import com.mycompany.datastock.service.dto.HistorialErrorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistorialError} and its DTO {@link HistorialErrorDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistorialErrorMapper extends EntityMapper<HistorialErrorDTO, HistorialError> {
    @Mapping(target = "cuenta", source = "cuenta", qualifiedByName = "cuentaNumeroDocumento")
    HistorialErrorDTO toDto(HistorialError s);

    @Named("cuentaNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    CuentaDTO toDtoCuentaNumeroDocumento(Cuenta cuenta);
}
