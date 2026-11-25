package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Lote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lote entity.
 */
@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
    default Optional<Lote> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Lote> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Lote> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select lote from Lote lote left join fetch lote.producto", countQuery = "select count(lote) from Lote lote")
    Page<Lote> findAllWithToOneRelationships(Pageable pageable);

    @Query("select lote from Lote lote left join fetch lote.producto")
    List<Lote> findAllWithToOneRelationships();

    @Query("select lote from Lote lote left join fetch lote.producto where lote.id =:id")
    Optional<Lote> findOneWithToOneRelationships(@Param("id") Long id);
}
