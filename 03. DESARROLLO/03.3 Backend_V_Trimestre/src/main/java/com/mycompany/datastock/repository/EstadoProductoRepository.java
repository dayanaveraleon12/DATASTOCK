package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.EstadoProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoProductoRepository extends JpaRepository<EstadoProducto, Long> {}
