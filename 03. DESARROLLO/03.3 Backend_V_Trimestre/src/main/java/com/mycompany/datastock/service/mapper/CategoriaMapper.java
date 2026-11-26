package com.mycompany.datastock.service.mapper;

import com.mycompany.datastock.domain.Categoria;
import com.mycompany.datastock.service.dto.CategoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {}
