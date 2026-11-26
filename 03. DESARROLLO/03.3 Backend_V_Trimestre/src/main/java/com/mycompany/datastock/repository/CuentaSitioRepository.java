package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.CuentaSitio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CuentaSitio entity.
 */
@Repository
public interface CuentaSitioRepository extends JpaRepository<CuentaSitio, Long> {
    default Optional<CuentaSitio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CuentaSitio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CuentaSitio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select cuentaSitio from CuentaSitio cuentaSitio left join fetch cuentaSitio.cuenta left join fetch cuentaSitio.sitio",
        countQuery = "select count(cuentaSitio) from CuentaSitio cuentaSitio"
    )
    Page<CuentaSitio> findAllWithToOneRelationships(Pageable pageable);

    @Query("select cuentaSitio from CuentaSitio cuentaSitio left join fetch cuentaSitio.cuenta left join fetch cuentaSitio.sitio")
    List<CuentaSitio> findAllWithToOneRelationships();

    @Query(
        "select cuentaSitio from CuentaSitio cuentaSitio left join fetch cuentaSitio.cuenta left join fetch cuentaSitio.sitio where cuentaSitio.id =:id"
    )
    Optional<CuentaSitio> findOneWithToOneRelationships(@Param("id") Long id);
}
