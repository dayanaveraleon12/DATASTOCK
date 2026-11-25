package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Salida;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SalidaRepositoryWithBagRelationships {
    Optional<Salida> fetchBagRelationships(Optional<Salida> salida);

    List<Salida> fetchBagRelationships(List<Salida> salidas);

    Page<Salida> fetchBagRelationships(Page<Salida> salidas);
}
