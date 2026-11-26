package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.HistorialErrorRepository;
import com.mycompany.datastock.service.HistorialErrorService;
import com.mycompany.datastock.service.dto.HistorialErrorDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.HistorialError}.
 */
@RestController
@RequestMapping("/api/historial-errors")
public class HistorialErrorResource {

    private static final Logger LOG = LoggerFactory.getLogger(HistorialErrorResource.class);

    private static final String ENTITY_NAME = "historialError";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final HistorialErrorService historialErrorService;

    private final HistorialErrorRepository historialErrorRepository;

    public HistorialErrorResource(HistorialErrorService historialErrorService, HistorialErrorRepository historialErrorRepository) {
        this.historialErrorService = historialErrorService;
        this.historialErrorRepository = historialErrorRepository;
    }

    /**
     * {@code POST  /historial-errors} : Create a new historialError.
     *
     * @param historialErrorDTO the historialErrorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historialErrorDTO, or with status {@code 400 (Bad Request)} if the historialError already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HistorialErrorDTO> createHistorialError(@Valid @RequestBody HistorialErrorDTO historialErrorDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save HistorialError : {}", historialErrorDTO);
        if (historialErrorDTO.getId() != null) {
            throw new BadRequestAlertException("A new historialError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        historialErrorDTO = historialErrorService.save(historialErrorDTO);
        return ResponseEntity.created(new URI("/api/historial-errors/" + historialErrorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, historialErrorDTO.getId().toString()))
            .body(historialErrorDTO);
    }

    /**
     * {@code PUT  /historial-errors/:id} : Updates an existing historialError.
     *
     * @param id the id of the historialErrorDTO to save.
     * @param historialErrorDTO the historialErrorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historialErrorDTO,
     * or with status {@code 400 (Bad Request)} if the historialErrorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historialErrorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistorialErrorDTO> updateHistorialError(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistorialErrorDTO historialErrorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HistorialError : {}, {}", id, historialErrorDTO);
        if (historialErrorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historialErrorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historialErrorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        historialErrorDTO = historialErrorService.update(historialErrorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historialErrorDTO.getId().toString()))
            .body(historialErrorDTO);
    }

    /**
     * {@code PATCH  /historial-errors/:id} : Partial updates given fields of an existing historialError, field will ignore if it is null
     *
     * @param id the id of the historialErrorDTO to save.
     * @param historialErrorDTO the historialErrorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historialErrorDTO,
     * or with status {@code 400 (Bad Request)} if the historialErrorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historialErrorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historialErrorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistorialErrorDTO> partialUpdateHistorialError(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistorialErrorDTO historialErrorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update HistorialError : {}, {}", id, historialErrorDTO);
        if (historialErrorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historialErrorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historialErrorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistorialErrorDTO> result = historialErrorService.partialUpdate(historialErrorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historialErrorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /historial-errors} : get all the Historial Errors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Historial Errors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HistorialErrorDTO>> getAllHistorialErrors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of HistorialErrors");
        Page<HistorialErrorDTO> page;
        if (eagerload) {
            page = historialErrorService.findAllWithEagerRelationships(pageable);
        } else {
            page = historialErrorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /historial-errors/:id} : get the "id" historialError.
     *
     * @param id the id of the historialErrorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historialErrorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistorialErrorDTO> getHistorialError(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HistorialError : {}", id);
        Optional<HistorialErrorDTO> historialErrorDTO = historialErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historialErrorDTO);
    }

    /**
     * {@code DELETE  /historial-errors/:id} : delete the "id" historialError.
     *
     * @param id the id of the historialErrorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialError(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HistorialError : {}", id);
        historialErrorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
