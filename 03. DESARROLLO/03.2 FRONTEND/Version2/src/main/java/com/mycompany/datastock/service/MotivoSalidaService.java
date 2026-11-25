package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.MotivoSalida}.
 */
public interface MotivoSalidaService {
    /**
     * Save a motivoSalida.
     *
     * @param motivoSalidaDTO the entity to save.
     * @return the persisted entity.
     */
    MotivoSalidaDTO save(MotivoSalidaDTO motivoSalidaDTO);

    /**
     * Updates a motivoSalida.
     *
     * @param motivoSalidaDTO the entity to update.
     * @return the persisted entity.
     */
    MotivoSalidaDTO update(MotivoSalidaDTO motivoSalidaDTO);

    /**
     * Partially updates a motivoSalida.
     *
     * @param motivoSalidaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MotivoSalidaDTO> partialUpdate(MotivoSalidaDTO motivoSalidaDTO);

    /**
     * Get all the motivoSalidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MotivoSalidaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" motivoSalida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MotivoSalidaDTO> findOne(Long id);

    /**
     * Delete the "id" motivoSalida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
