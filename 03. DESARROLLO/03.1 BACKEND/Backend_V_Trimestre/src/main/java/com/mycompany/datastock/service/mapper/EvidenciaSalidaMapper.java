package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.EvidenciaSalida;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.service.dto.CuentaDTO;
import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
import com.mycompany.datastock.service.dto.SitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvidenciaSalida} and its DTO {@link EvidenciaSalidaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EvidenciaSalidaMapper extends EntityMapper<EvidenciaSalidaDTO, EvidenciaSalida> {
    @Mapping(target = "cuenta", source = "cuenta", qualifiedByName = "cuentaNumeroDocumento")
    @Mapping(target = "sitioSubida", source = "sitioSubida", qualifiedByName = "sitioNombre")
    EvidenciaSalidaDTO toDto(EvidenciaSalida s);

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
