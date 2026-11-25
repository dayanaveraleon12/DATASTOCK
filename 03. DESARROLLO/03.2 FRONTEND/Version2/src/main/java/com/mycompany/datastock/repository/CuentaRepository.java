package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Cuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cuenta entity.
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    default Optional<Cuenta> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Cuenta> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Cuenta> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select cuenta from Cuenta cuenta left join fetch cuenta.user left join fetch cuenta.tipoDocumento",
        countQuery = "select count(cuenta) from Cuenta cuenta"
    )
    Page<Cuenta> findAllWithToOneRelationships(Pageable pageable);

    @Query("select cuenta from Cuenta cuenta left join fetch cuenta.user left join fetch cuenta.tipoDocumento")
    List<Cuenta> findAllWithToOneRelationships();

    @Query("select cuenta from Cuenta cuenta left join fetch cuenta.user left join fetch cuenta.tipoDocumento where cuenta.id =:id")
    Optional<Cuenta> findOneWithToOneRelationships(@Param("id") Long id);
}
