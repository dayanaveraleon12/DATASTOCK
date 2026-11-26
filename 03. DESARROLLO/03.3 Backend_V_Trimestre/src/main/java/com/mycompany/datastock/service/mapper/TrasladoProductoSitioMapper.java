package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Traslado;
import com.mycompany.datastock.domain.TrasladoProductoSitio;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.dto.TrasladoDTO;
import com.mycompany.datastock.service.dto.TrasladoProductoSitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrasladoProductoSitio} and its DTO {@link TrasladoProductoSitioDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrasladoProductoSitioMapper extends EntityMapper<TrasladoProductoSitioDTO, TrasladoProductoSitio> {
    @Mapping(target = "traslado", source = "traslado", qualifiedByName = "trasladoId")
    @Mapping(target = "productoSitio", source = "productoSitio", qualifiedByName = "productoSitioId")
    TrasladoProductoSitioDTO toDto(TrasladoProductoSitio s);

    @Named("trasladoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrasladoDTO toDtoTrasladoId(Traslado traslado);

    @Named("productoSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoSitioDTO toDtoProductoSitioId(ProductoSitio productoSitio);
}
