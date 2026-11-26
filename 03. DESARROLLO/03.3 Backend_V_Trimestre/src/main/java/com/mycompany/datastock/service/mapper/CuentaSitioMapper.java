package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.service.dto.CuentaDTO;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import com.mycompany.datastock.service.dto.SitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CuentaSitio} and its DTO {@link CuentaSitioDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentaSitioMapper extends EntityMapper<CuentaSitioDTO, CuentaSitio> {
    @Mapping(target = "cuenta", source = "cuenta", qualifiedByName = "cuentaNumeroDocumento")
    @Mapping(target = "sitio", source = "sitio", qualifiedByName = "sitioNombre")
    CuentaSitioDTO toDto(CuentaSitio s);

    @Named("cuentaNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    CuentaDTO toDtoCuentaNumeroDocumento(Cuenta cuenta);

    @Named("sitioNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SitioDTO toDtoSitioNombre(Sitio sitio);
}
