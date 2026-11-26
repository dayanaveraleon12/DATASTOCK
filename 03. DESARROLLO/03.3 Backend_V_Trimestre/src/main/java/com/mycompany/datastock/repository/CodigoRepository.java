package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Codigo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Codigo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodigoRepository extends JpaRepository<Codigo, Long> {}
