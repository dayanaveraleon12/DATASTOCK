package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.HistorialError;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HistorialError entity.
 */
@Repository
public interface HistorialErrorRepository extends JpaRepository<HistorialError, Long> {
    default Optional<HistorialError> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<HistorialError> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<HistorialError> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select historialError from HistorialError historialError left join fetch historialError.cuenta",
        countQuery = "select count(historialError) from HistorialError historialError"
    )
    Page<HistorialError> findAllWithToOneRelationships(Pageable pageable);

    @Query("select historialError from HistorialError historialError left join fetch historialError.cuenta")
    List<HistorialError> findAllWithToOneRelationships();

    @Query("select historialError from HistorialError historialError left join fetch historialError.cuenta where historialError.id =:id")
    Optional<HistorialError> findOneWithToOneRelationships(@Param("id") Long id);
}
