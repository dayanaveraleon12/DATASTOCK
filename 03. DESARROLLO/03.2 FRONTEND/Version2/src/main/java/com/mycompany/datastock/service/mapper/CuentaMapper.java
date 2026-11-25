package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.TipoDocumento;
import com.mycompany.datastock.domain.User;
import com.mycompany.datastock.service.dto.CuentaDTO;
import com.mycompany.datastock.service.dto.TipoDocumentoDTO;
import com.mycompany.datastock.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuenta} and its DTO {@link CuentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper extends EntityMapper<CuentaDTO, Cuenta> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "tipoDocumentoTipoDocumento")
    CuentaDTO toDto(Cuenta s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tipoDocumentoTipoDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento")
    TipoDocumentoDTO toDtoTipoDocumentoTipoDocumento(TipoDocumento tipoDocumento);
}
