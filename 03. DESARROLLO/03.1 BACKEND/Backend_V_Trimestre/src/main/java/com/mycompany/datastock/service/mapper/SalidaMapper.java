package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.EvidenciaSalida;
import com.mycompany.datastock.domain.MotivoSalida;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Salida;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.dto.SalidaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Salida} and its DTO {@link SalidaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalidaMapper extends EntityMapper<SalidaDTO, Salida> {
    @Mapping(target = "evidenciaSalida", source = "evidenciaSalida", qualifiedByName = "evidenciaSalidaId")
    @Mapping(target = "cuentaSitio", source = "cuentaSitio", qualifiedByName = "cuentaSitioId")
    @Mapping(target = "motivoSalida", source = "motivoSalida", qualifiedByName = "motivoSalidaTipoMotivo")
    @Mapping(target = "productoSitioses", source = "productoSitioses", qualifiedByName = "productoSitioIdSet")
    SalidaDTO toDto(Salida s);

    @Mapping(target = "removeProductoSitios", ignore = true)
    Salida toEntity(SalidaDTO salidaDTO);

    @Named("evidenciaSalidaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EvidenciaSalidaDTO toDtoEvidenciaSalidaId(EvidenciaSalida evidenciaSalida);

    @Named("cuentaSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaSitioDTO toDtoCuentaSitioId(CuentaSitio cuentaSitio);

    @Named("motivoSalidaTipoMotivo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoMotivo", source = "tipoMotivo")
    MotivoSalidaDTO toDtoMotivoSalidaTipoMotivo(MotivoSalida motivoSalida);

    @Named("productoSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoSitioDTO toDtoProductoSitioId(ProductoSitio productoSitio);

    @Named("productoSitioIdSet")
    default Set<ProductoSitioDTO> toDtoProductoSitioIdSet(Set<ProductoSitio> productoSitio) {
        return productoSitio.stream().map(this::toDtoProductoSitioId).collect(Collectors.toSet());
    }
}
