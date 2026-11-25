package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.EvidenciaSalidaRepository;
import com.mycompany.datastock.service.EvidenciaSalidaService;
import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.EvidenciaSalida}.
 */
@RestController
@RequestMapping("/api/evidencia-salidas")
public class EvidenciaSalidaResource {

    private static final Logger LOG = LoggerFactory.getLogger(EvidenciaSalidaResource.class);

    private static final String ENTITY_NAME = "evidenciaSalida";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final EvidenciaSalidaService evidenciaSalidaService;

    private final EvidenciaSalidaRepository evidenciaSalidaRepository;

    public EvidenciaSalidaResource(EvidenciaSalidaService evidenciaSalidaService, EvidenciaSalidaRepository evidenciaSalidaRepository) {
        this.evidenciaSalidaService = evidenciaSalidaService;
        this.evidenciaSalidaRepository = evidenciaSalidaRepository;
    }

    /**
     * {@code POST  /evidencia-salidas} : Create a new evidenciaSalida.
     *
     * @param evidenciaSalidaDTO the evidenciaSalidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evidenciaSalidaDTO, or with status {@code 400 (Bad Request)} if the evidenciaSalida already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EvidenciaSalidaDTO> createEvidenciaSalida(@Valid @RequestBody EvidenciaSalidaDTO evidenciaSalidaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EvidenciaSalida : {}", evidenciaSalidaDTO);
        if (evidenciaSalidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new evidenciaSalida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        evidenciaSalidaDTO = evidenciaSalidaService.save(evidenciaSalidaDTO);
        return ResponseEntity.created(new URI("/api/evidencia-salidas/" + evidenciaSalidaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, evidenciaSalidaDTO.getId().toString()))
            .body(evidenciaSalidaDTO);
    }

    /**
     * {@code PUT  /evidencia-salidas/:id} : Updates an existing evidenciaSalida.
     *
     * @param id the id of the evidenciaSalidaDTO to save.
     * @param evidenciaSalidaDTO the evidenciaSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evidenciaSalidaDTO,
     * or with status {@code 400 (Bad Request)} if the evidenciaSalidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evidenciaSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EvidenciaSalidaDTO> updateEvidenciaSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EvidenciaSalidaDTO evidenciaSalidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EvidenciaSalida : {}, {}", id, evidenciaSalidaDTO);
        if (evidenciaSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evidenciaSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evidenciaSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        evidenciaSalidaDTO = evidenciaSalidaService.update(evidenciaSalidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evidenciaSalidaDTO.getId().toString()))
            .body(evidenciaSalidaDTO);
    }

    /**
     * {@code PATCH  /evidencia-salidas/:id} : Partial updates given fields of an existing evidenciaSalida, field will ignore if it is null
     *
     * @param id the id of the evidenciaSalidaDTO to save.
     * @param evidenciaSalidaDTO the evidenciaSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evidenciaSalidaDTO,
     * or with status {@code 400 (Bad Request)} if the evidenciaSalidaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evidenciaSalidaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evidenciaSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EvidenciaSalidaDTO> partialUpdateEvidenciaSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EvidenciaSalidaDTO evidenciaSalidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update EvidenciaSalida : {}, {}", id, evidenciaSalidaDTO);
        if (evidenciaSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evidenciaSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evidenciaSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvidenciaSalidaDTO> result = evidenciaSalidaService.partialUpdate(evidenciaSalidaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evidenciaSalidaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evidencia-salidas} : get all the Evidencia Salidas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Evidencia Salidas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EvidenciaSalidaDTO>> getAllEvidenciaSalidas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of EvidenciaSalidas");
        Page<EvidenciaSalidaDTO> page;
        if (eagerload) {
            page = evidenciaSalidaService.findAllWithEagerRelationships(pageable);
        } else {
            page = evidenciaSalidaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evidencia-salidas/:id} : get the "id" evidenciaSalida.
     *
     * @param id the id of the evidenciaSalidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evidenciaSalidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EvidenciaSalidaDTO> getEvidenciaSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EvidenciaSalida : {}", id);
        Optional<EvidenciaSalidaDTO> evidenciaSalidaDTO = evidenciaSalidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evidenciaSalidaDTO);
    }

    /**
     * {@code DELETE  /evidencia-salidas/:id} : delete the "id" evidenciaSalida.
     *
     * @param id the id of the evidenciaSalidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvidenciaSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EvidenciaSalida : {}", id);
        evidenciaSalidaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
