package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.EvidenciaSalida;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EvidenciaSalida entity.
 */
@Repository
public interface EvidenciaSalidaRepository extends JpaRepository<EvidenciaSalida, Long> {
    default Optional<EvidenciaSalida> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EvidenciaSalida> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EvidenciaSalida> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select evidenciaSalida from EvidenciaSalida evidenciaSalida left join fetch evidenciaSalida.cuenta left join fetch evidenciaSalida.sitioSubida",
        countQuery = "select count(evidenciaSalida) from EvidenciaSalida evidenciaSalida"
    )
    Page<EvidenciaSalida> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select evidenciaSalida from EvidenciaSalida evidenciaSalida left join fetch evidenciaSalida.cuenta left join fetch evidenciaSalida.sitioSubida"
    )
    List<EvidenciaSalida> findAllWithToOneRelationships();

    @Query(
        "select evidenciaSalida from EvidenciaSalida evidenciaSalida left join fetch evidenciaSalida.cuenta left join fetch evidenciaSalida.sitioSubida where evidenciaSalida.id =:id"
    )
    Optional<EvidenciaSalida> findOneWithToOneRelationships(@Param("id") Long id);
}
