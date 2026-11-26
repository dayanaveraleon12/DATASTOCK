package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Remision;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.service.dto.CodigoDTO;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.dto.RemisionDTO;
import com.mycompany.datastock.service.dto.SitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remision} and its DTO {@link RemisionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemisionMapper extends EntityMapper<RemisionDTO, Remision> {
    @Mapping(target = "productoSitio", source = "productoSitio", qualifiedByName = "productoSitioId")
    @Mapping(target = "codigo", source = "codigo", qualifiedByName = "codigoId")
    @Mapping(target = "sitioDestino", source = "sitioDestino", qualifiedByName = "sitioNombre")
    RemisionDTO toDto(Remision s);

    @Named("productoSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoSitioDTO toDtoProductoSitioId(ProductoSitio productoSitio);

    @Named("codigoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CodigoDTO toDtoCodigoId(Codigo codigo);

    @Named("sitioNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SitioDTO toDtoSitioNombre(Sitio sitio);
}
