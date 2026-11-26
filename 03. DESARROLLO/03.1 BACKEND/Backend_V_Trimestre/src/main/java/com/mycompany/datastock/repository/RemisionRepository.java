package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Remision;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Remision entity.
 */
@Repository
public interface RemisionRepository extends JpaRepository<Remision, Long> {
    default Optional<Remision> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Remision> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Remision> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select remision from Remision remision left join fetch remision.sitioDestino",
        countQuery = "select count(remision) from Remision remision"
    )
    Page<Remision> findAllWithToOneRelationships(Pageable pageable);

    @Query("select remision from Remision remision left join fetch remision.sitioDestino")
    List<Remision> findAllWithToOneRelationships();

    @Query("select remision from Remision remision left join fetch remision.sitioDestino where remision.id =:id")
    Optional<Remision> findOneWithToOneRelationships(@Param("id") Long id);
}
