package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.domain.Traslado;
import com.mycompany.datastock.service.dto.CodigoDTO;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import com.mycompany.datastock.service.dto.SitioDTO;
import com.mycompany.datastock.service.dto.TrasladoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Traslado} and its DTO {@link TrasladoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrasladoMapper extends EntityMapper<TrasladoDTO, Traslado> {
    @Mapping(target = "cuentaSitio", source = "cuentaSitio", qualifiedByName = "cuentaSitioId")
    @Mapping(target = "sitioDestino", source = "sitioDestino", qualifiedByName = "sitioNombre")
    @Mapping(target = "codigo", source = "codigo", qualifiedByName = "codigoId")
    TrasladoDTO toDto(Traslado s);

    @Named("cuentaSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaSitioDTO toDtoCuentaSitioId(CuentaSitio cuentaSitio);

    @Named("sitioNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SitioDTO toDtoSitioNombre(Sitio sitio);

    @Named("codigoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CodigoDTO toDtoCodigoId(Codigo codigo);
}
