package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.CuentaSitioRepository;
import com.mycompany.datastock.service.CuentaSitioService;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.CuentaSitio}.
 */
@RestController
@RequestMapping("/api/cuenta-sitios")
public class CuentaSitioResource {

    private static final Logger LOG = LoggerFactory.getLogger(CuentaSitioResource.class);

    private static final String ENTITY_NAME = "cuentaSitio";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final CuentaSitioService cuentaSitioService;

    private final CuentaSitioRepository cuentaSitioRepository;

    public CuentaSitioResource(CuentaSitioService cuentaSitioService, CuentaSitioRepository cuentaSitioRepository) {
        this.cuentaSitioService = cuentaSitioService;
        this.cuentaSitioRepository = cuentaSitioRepository;
    }

    /**
     * {@code POST  /cuenta-sitios} : Create a new cuentaSitio.
     *
     * @param cuentaSitioDTO the cuentaSitioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuentaSitioDTO, or with status {@code 400 (Bad Request)} if the cuentaSitio already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CuentaSitioDTO> createCuentaSitio(@Valid @RequestBody CuentaSitioDTO cuentaSitioDTO) throws URISyntaxException {
        LOG.debug("REST request to save CuentaSitio : {}", cuentaSitioDTO);
        if (cuentaSitioDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuentaSitio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cuentaSitioDTO = cuentaSitioService.save(cuentaSitioDTO);
        return ResponseEntity.created(new URI("/api/cuenta-sitios/" + cuentaSitioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cuentaSitioDTO.getId().toString()))
            .body(cuentaSitioDTO);
    }

    /**
     * {@code PUT  /cuenta-sitios/:id} : Updates an existing cuentaSitio.
     *
     * @param id the id of the cuentaSitioDTO to save.
     * @param cuentaSitioDTO the cuentaSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentaSitioDTO,
     * or with status {@code 400 (Bad Request)} if the cuentaSitioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuentaSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuentaSitioDTO> updateCuentaSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CuentaSitioDTO cuentaSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CuentaSitio : {}, {}", id, cuentaSitioDTO);
        if (cuentaSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuentaSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuentaSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cuentaSitioDTO = cuentaSitioService.update(cuentaSitioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cuentaSitioDTO.getId().toString()))
            .body(cuentaSitioDTO);
    }

    /**
     * {@code PATCH  /cuenta-sitios/:id} : Partial updates given fields of an existing cuentaSitio, field will ignore if it is null
     *
     * @param id the id of the cuentaSitioDTO to save.
     * @param cuentaSitioDTO the cuentaSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentaSitioDTO,
     * or with status {@code 400 (Bad Request)} if the cuentaSitioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cuentaSitioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cuentaSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CuentaSitioDTO> partialUpdateCuentaSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CuentaSitioDTO cuentaSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update CuentaSitio : {}, {}", id, cuentaSitioDTO);
        if (cuentaSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuentaSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuentaSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CuentaSitioDTO> result = cuentaSitioService.partialUpdate(cuentaSitioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cuentaSitioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cuenta-sitios} : get all the Cuenta Sitios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Cuenta Sitios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CuentaSitioDTO>> getAllCuentaSitios(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of CuentaSitios");
        Page<CuentaSitioDTO> page;
        if (eagerload) {
            page = cuentaSitioService.findAllWithEagerRelationships(pageable);
        } else {
            page = cuentaSitioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cuenta-sitios/:id} : get the "id" cuentaSitio.
     *
     * @param id the id of the cuentaSitioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuentaSitioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuentaSitioDTO> getCuentaSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CuentaSitio : {}", id);
        Optional<CuentaSitioDTO> cuentaSitioDTO = cuentaSitioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuentaSitioDTO);
    }

    /**
     * {@code DELETE  /cuenta-sitios/:id} : delete the "id" cuentaSitio.
     *
     * @param id the id of the cuentaSitioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CuentaSitio : {}", id);
        cuentaSitioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
