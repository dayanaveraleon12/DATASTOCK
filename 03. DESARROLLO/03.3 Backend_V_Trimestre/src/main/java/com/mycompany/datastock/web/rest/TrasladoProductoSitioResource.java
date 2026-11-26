package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.TrasladoProductoSitioRepository;
import com.mycompany.datastock.service.TrasladoProductoSitioService;
import com.mycompany.datastock.service.dto.TrasladoProductoSitioDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.TrasladoProductoSitio}.
 */
@RestController
@RequestMapping("/api/traslado-producto-sitios")
public class TrasladoProductoSitioResource {

    private static final Logger LOG = LoggerFactory.getLogger(TrasladoProductoSitioResource.class);

    private static final String ENTITY_NAME = "trasladoProductoSitio";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final TrasladoProductoSitioService trasladoProductoSitioService;

    private final TrasladoProductoSitioRepository trasladoProductoSitioRepository;

    public TrasladoProductoSitioResource(
        TrasladoProductoSitioService trasladoProductoSitioService,
        TrasladoProductoSitioRepository trasladoProductoSitioRepository
    ) {
        this.trasladoProductoSitioService = trasladoProductoSitioService;
        this.trasladoProductoSitioRepository = trasladoProductoSitioRepository;
    }

    /**
     * {@code POST  /traslado-producto-sitios} : Create a new trasladoProductoSitio.
     *
     * @param trasladoProductoSitioDTO the trasladoProductoSitioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trasladoProductoSitioDTO, or with status {@code 400 (Bad Request)} if the trasladoProductoSitio already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TrasladoProductoSitioDTO> createTrasladoProductoSitio(
        @Valid @RequestBody TrasladoProductoSitioDTO trasladoProductoSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save TrasladoProductoSitio : {}", trasladoProductoSitioDTO);
        if (trasladoProductoSitioDTO.getId() != null) {
            throw new BadRequestAlertException("A new trasladoProductoSitio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trasladoProductoSitioDTO = trasladoProductoSitioService.save(trasladoProductoSitioDTO);
        return ResponseEntity.created(new URI("/api/traslado-producto-sitios/" + trasladoProductoSitioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trasladoProductoSitioDTO.getId().toString()))
            .body(trasladoProductoSitioDTO);
    }

    /**
     * {@code PUT  /traslado-producto-sitios/:id} : Updates an existing trasladoProductoSitio.
     *
     * @param id the id of the trasladoProductoSitioDTO to save.
     * @param trasladoProductoSitioDTO the trasladoProductoSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trasladoProductoSitioDTO,
     * or with status {@code 400 (Bad Request)} if the trasladoProductoSitioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trasladoProductoSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrasladoProductoSitioDTO> updateTrasladoProductoSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrasladoProductoSitioDTO trasladoProductoSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TrasladoProductoSitio : {}, {}", id, trasladoProductoSitioDTO);
        if (trasladoProductoSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trasladoProductoSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trasladoProductoSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trasladoProductoSitioDTO = trasladoProductoSitioService.update(trasladoProductoSitioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trasladoProductoSitioDTO.getId().toString()))
            .body(trasladoProductoSitioDTO);
    }

    /**
     * {@code PATCH  /traslado-producto-sitios/:id} : Partial updates given fields of an existing trasladoProductoSitio, field will ignore if it is null
     *
     * @param id the id of the trasladoProductoSitioDTO to save.
     * @param trasladoProductoSitioDTO the trasladoProductoSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trasladoProductoSitioDTO,
     * or with status {@code 400 (Bad Request)} if the trasladoProductoSitioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trasladoProductoSitioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trasladoProductoSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrasladoProductoSitioDTO> partialUpdateTrasladoProductoSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrasladoProductoSitioDTO trasladoProductoSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update TrasladoProductoSitio : {}, {}", id, trasladoProductoSitioDTO);
        if (trasladoProductoSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trasladoProductoSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trasladoProductoSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrasladoProductoSitioDTO> result = trasladoProductoSitioService.partialUpdate(trasladoProductoSitioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trasladoProductoSitioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /traslado-producto-sitios} : get all the Traslado Producto Sitios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Traslado Producto Sitios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TrasladoProductoSitioDTO>> getAllTrasladoProductoSitios(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TrasladoProductoSitios");
        Page<TrasladoProductoSitioDTO> page = trasladoProductoSitioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /traslado-producto-sitios/:id} : get the "id" trasladoProductoSitio.
     *
     * @param id the id of the trasladoProductoSitioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trasladoProductoSitioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrasladoProductoSitioDTO> getTrasladoProductoSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TrasladoProductoSitio : {}", id);
        Optional<TrasladoProductoSitioDTO> trasladoProductoSitioDTO = trasladoProductoSitioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trasladoProductoSitioDTO);
    }

    /**
     * {@code DELETE  /traslado-producto-sitios/:id} : delete the "id" trasladoProductoSitio.
     *
     * @param id the id of the trasladoProductoSitioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrasladoProductoSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TrasladoProductoSitio : {}", id);
        trasladoProductoSitioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
