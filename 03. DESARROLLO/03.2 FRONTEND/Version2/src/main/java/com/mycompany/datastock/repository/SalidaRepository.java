package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Salida;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Salida entity.
 *
 * When extending this class, extend SalidaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SalidaRepository extends SalidaRepositoryWithBagRelationships, JpaRepository<Salida, Long> {
    default Optional<Salida> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Salida> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Salida> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select salida from Salida salida left join fetch salida.motivoSalida",
        countQuery = "select count(salida) from Salida salida"
    )
    Page<Salida> findAllWithToOneRelationships(Pageable pageable);

    @Query("select salida from Salida salida left join fetch salida.motivoSalida")
    List<Salida> findAllWithToOneRelationships();

    @Query("select salida from Salida salida left join fetch salida.motivoSalida where salida.id =:id")
    Optional<Salida> findOneWithToOneRelationships(@Param("id") Long id);
}
