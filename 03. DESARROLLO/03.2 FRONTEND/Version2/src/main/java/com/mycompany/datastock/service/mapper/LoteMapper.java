package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Lote;
import com.mycompany.datastock.domain.Producto;
import com.mycompany.datastock.service.dto.LoteDTO;
import com.mycompany.datastock.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lote} and its DTO {@link LoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoteMapper extends EntityMapper<LoteDTO, Lote> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "productoNombreEspecificacion")
    LoteDTO toDto(Lote s);

    @Named("productoNombreEspecificacion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreEspecificacion", source = "nombreEspecificacion")
    ProductoDTO toDtoProductoNombreEspecificacion(Producto producto);
}
