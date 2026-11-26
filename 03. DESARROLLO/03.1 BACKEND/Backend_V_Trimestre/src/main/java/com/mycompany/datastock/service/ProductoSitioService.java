package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.ProductoSitio}.
 */
public interface ProductoSitioService {
    /**
     * Save a productoSitio.
     *
     * @param productoSitioDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoSitioDTO save(ProductoSitioDTO productoSitioDTO);

    /**
     * Updates a productoSitio.
     *
     * @param productoSitioDTO the entity to update.
     * @return the persisted entity.
     */
    ProductoSitioDTO update(ProductoSitioDTO productoSitioDTO);

    /**
     * Partially updates a productoSitio.
     *
     * @param productoSitioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoSitioDTO> partialUpdate(ProductoSitioDTO productoSitioDTO);

    /**
     * Get all the productoSitios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoSitioDTO> findAll(Pageable pageable);

    /**
     * Get all the productoSitios with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoSitioDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" productoSitio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoSitioDTO> findOne(Long id);

    /**
     * Delete the "id" productoSitio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
