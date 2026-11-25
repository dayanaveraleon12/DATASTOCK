package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Traslado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Traslado entity.
 */
@Repository
public interface TrasladoRepository extends JpaRepository<Traslado, Long> {
    default Optional<Traslado> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Traslado> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Traslado> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select traslado from Traslado traslado left join fetch traslado.sitioDestino",
        countQuery = "select count(traslado) from Traslado traslado"
    )
    Page<Traslado> findAllWithToOneRelationships(Pageable pageable);

    @Query("select traslado from Traslado traslado left join fetch traslado.sitioDestino")
    List<Traslado> findAllWithToOneRelationships();

    @Query("select traslado from Traslado traslado left join fetch traslado.sitioDestino where traslado.id =:id")
    Optional<Traslado> findOneWithToOneRelationships(@Param("id") Long id);
}
