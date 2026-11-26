package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Sitio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sitio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SitioRepository extends JpaRepository<Sitio, Long> {}
