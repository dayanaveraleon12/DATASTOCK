package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.MotivoSalida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MotivoSalida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivoSalidaRepository extends JpaRepository<MotivoSalida, Long> {}
