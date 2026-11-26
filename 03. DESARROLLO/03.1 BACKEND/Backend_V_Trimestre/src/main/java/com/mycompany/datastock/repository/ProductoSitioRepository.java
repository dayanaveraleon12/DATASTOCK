package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.ProductoSitio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductoSitio entity.
 */
@Repository
public interface ProductoSitioRepository extends JpaRepository<ProductoSitio, Long> {
    default Optional<ProductoSitio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProductoSitio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProductoSitio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select productoSitio from ProductoSitio productoSitio left join fetch productoSitio.producto left join fetch productoSitio.sitio left join fetch productoSitio.lote left join fetch productoSitio.estadoProducto",
        countQuery = "select count(productoSitio) from ProductoSitio productoSitio"
    )
    Page<ProductoSitio> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select productoSitio from ProductoSitio productoSitio left join fetch productoSitio.producto left join fetch productoSitio.sitio left join fetch productoSitio.lote left join fetch productoSitio.estadoProducto"
    )
    List<ProductoSitio> findAllWithToOneRelationships();

    @Query(
        "select productoSitio from ProductoSitio productoSitio left join fetch productoSitio.producto left join fetch productoSitio.sitio left join fetch productoSitio.lote left join fetch productoSitio.estadoProducto where productoSitio.id =:id"
    )
    Optional<ProductoSitio> findOneWithToOneRelationships(@Param("id") Long id);
}
