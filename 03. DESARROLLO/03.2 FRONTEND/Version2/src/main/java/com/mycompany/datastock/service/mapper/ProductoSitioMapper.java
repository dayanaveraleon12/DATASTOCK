package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.EstadoProducto;
import com.mycompany.datastock.domain.Lote;
import com.mycompany.datastock.domain.Producto;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Salida;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.service.dto.EstadoProductoDTO;
import com.mycompany.datastock.service.dto.LoteDTO;
import com.mycompany.datastock.service.dto.ProductoDTO;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.dto.SalidaDTO;
import com.mycompany.datastock.service.dto.SitioDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoSitio} and its DTO {@link ProductoSitioDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoSitioMapper extends EntityMapper<ProductoSitioDTO, ProductoSitio> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "productoNombreEspecificacion")
    @Mapping(target = "sitio", source = "sitio", qualifiedByName = "sitioNombre")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "loteCodigoLote")
    @Mapping(target = "estadoProducto", source = "estadoProducto", qualifiedByName = "estadoProductoTipoEstado")
    @Mapping(target = "salidases", source = "salidases", qualifiedByName = "salidaIdSet")
    ProductoSitioDTO toDto(ProductoSitio s);

    @Mapping(target = "salidases", ignore = true)
    @Mapping(target = "removeSalidas", ignore = true)
    ProductoSitio toEntity(ProductoSitioDTO productoSitioDTO);

    @Named("productoNombreEspecificacion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreEspecificacion", source = "nombreEspecificacion")
    ProductoDTO toDtoProductoNombreEspecificacion(Producto producto);

    @Named("sitioNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SitioDTO toDtoSitioNombre(Sitio sitio);

    @Named("loteCodigoLote")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoLote", source = "codigoLote")
    LoteDTO toDtoLoteCodigoLote(Lote lote);

    @Named("estadoProductoTipoEstado")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoEstado", source = "tipoEstado")
    EstadoProductoDTO toDtoEstadoProductoTipoEstado(EstadoProducto estadoProducto);

    @Named("salidaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SalidaDTO toDtoSalidaId(Salida salida);

    @Named("salidaIdSet")
    default Set<SalidaDTO> toDtoSalidaIdSet(Set<Salida> salida) {
        return salida.stream().map(this::toDtoSalidaId).collect(Collectors.toSet());
    }
}
