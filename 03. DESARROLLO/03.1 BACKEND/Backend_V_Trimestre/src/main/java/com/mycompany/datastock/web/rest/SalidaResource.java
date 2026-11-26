package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.SalidaRepository;
import com.mycompany.datastock.service.SalidaService;
import com.mycompany.datastock.service.dto.SalidaDTO;
import com.mycompany.datastock.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.datastock.domain.Salida}.
 */
@RestController
@RequestMapping("/api/salidas")
public class SalidaResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalidaResource.class);

    private static final String ENTITY_NAME = "salida";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final SalidaService salidaService;

    private final SalidaRepository salidaRepository;

    public SalidaResource(SalidaService salidaService, SalidaRepository salidaRepository) {
        this.salidaService = salidaService;
        this.salidaRepository = salidaRepository;
    }

    /**
     * {@code POST  /salidas} : Create a new salida.
     *
     * @param salidaDTO the salidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salidaDTO, or with status {@code 400 (Bad Request)} if the salida already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalidaDTO> createSalida(@Valid @RequestBody SalidaDTO salidaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Salida : {}", salidaDTO);
        if (salidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new salida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salidaDTO = salidaService.save(salidaDTO);
        return ResponseEntity.created(new URI("/api/salidas/" + salidaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salidaDTO.getId().toString()))
            .body(salidaDTO);
    }

    /**
     * {@code PUT  /salidas/:id} : Updates an existing salida.
     *
     * @param id the id of the salidaDTO to save.
     * @param salidaDTO the salidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salidaDTO,
     * or with status {@code 400 (Bad Request)} if the salidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalidaDTO> updateSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalidaDTO salidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Salida : {}, {}", id, salidaDTO);
        if (salidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salidaDTO = salidaService.update(salidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salidaDTO.getId().toString()))
            .body(salidaDTO);
    }

    /**
     * {@code PATCH  /salidas/:id} : Partial updates given fields of an existing salida, field will ignore if it is null
     *
     * @param id the id of the salidaDTO to save.
     * @param salidaDTO the salidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salidaDTO,
     * or with status {@code 400 (Bad Request)} if the salidaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salidaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalidaDTO> partialUpdateSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalidaDTO salidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Salida : {}, {}", id, salidaDTO);
        if (salidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalidaDTO> result = salidaService.partialUpdate(salidaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salidaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /salidas} : get all the Salidas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Salidas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalidaDTO>> getAllSalidas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Salidas");
        Page<SalidaDTO> page;
        if (eagerload) {
            page = salidaService.findAllWithEagerRelationships(pageable);
        } else {
            page = salidaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salidas/:id} : get the "id" salida.
     *
     * @param id the id of the salidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalidaDTO> getSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Salida : {}", id);
        Optional<SalidaDTO> salidaDTO = salidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salidaDTO);
    }

    /**
     * {@code DELETE  /salidas/:id} : delete the "id" salida.
     *
     * @param id the id of the salidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Salida : {}", id);
        salidaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
