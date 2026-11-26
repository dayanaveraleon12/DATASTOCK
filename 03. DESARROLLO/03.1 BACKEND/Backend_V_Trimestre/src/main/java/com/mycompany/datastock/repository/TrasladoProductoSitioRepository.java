package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.TrasladoProductoSitio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrasladoProductoSitio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrasladoProductoSitioRepository extends JpaRepository<TrasladoProductoSitio, Long> {}
