package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Categoria;
import com.mycompany.datastock.domain.Marca;
import com.mycompany.datastock.domain.Presentacion;
import com.mycompany.datastock.domain.Producto;
import com.mycompany.datastock.service.dto.CategoriaDTO;
import com.mycompany.datastock.service.dto.MarcaDTO;
import com.mycompany.datastock.service.dto.PresentacionDTO;
import com.mycompany.datastock.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoriaNombre")
    @Mapping(target = "presentacion", source = "presentacion", qualifiedByName = "presentacionTipoPresentacion")
    @Mapping(target = "marca", source = "marca", qualifiedByName = "marcaMarca")
    ProductoDTO toDto(Producto s);

    @Named("categoriaNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    CategoriaDTO toDtoCategoriaNombre(Categoria categoria);

    @Named("presentacionTipoPresentacion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoPresentacion", source = "tipoPresentacion")
    PresentacionDTO toDtoPresentacionTipoPresentacion(Presentacion presentacion);

    @Named("marcaMarca")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "marca", source = "marca")
    MarcaDTO toDtoMarcaMarca(Marca marca);
}
