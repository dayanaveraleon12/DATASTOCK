package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.service.dto.CodigoDTO;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Codigo} and its DTO {@link CodigoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CodigoMapper extends EntityMapper<CodigoDTO, Codigo> {
    @Mapping(target = "cuentaSitio", source = "cuentaSitio", qualifiedByName = "cuentaSitioId")
    CodigoDTO toDto(Codigo s);

    @Named("cuentaSitioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaSitioDTO toDtoCuentaSitioId(CuentaSitio cuentaSitio);
}
