package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.RemisionRepository;
import com.mycompany.datastock.service.RemisionService;
import com.mycompany.datastock.service.dto.RemisionDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.Remision}.
 */
@RestController
@RequestMapping("/api/remisions")
public class RemisionResource {

    private static final Logger LOG = LoggerFactory.getLogger(RemisionResource.class);

    private static final String ENTITY_NAME = "remision";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final RemisionService remisionService;

    private final RemisionRepository remisionRepository;

    public RemisionResource(RemisionService remisionService, RemisionRepository remisionRepository) {
        this.remisionService = remisionService;
        this.remisionRepository = remisionRepository;
    }

    /**
     * {@code POST  /remisions} : Create a new remision.
     *
     * @param remisionDTO the remisionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remisionDTO, or with status {@code 400 (Bad Request)} if the remision already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RemisionDTO> createRemision(@Valid @RequestBody RemisionDTO remisionDTO) throws URISyntaxException {
        LOG.debug("REST request to save Remision : {}", remisionDTO);
        if (remisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new remision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        remisionDTO = remisionService.save(remisionDTO);
        return ResponseEntity.created(new URI("/api/remisions/" + remisionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, remisionDTO.getId().toString()))
            .body(remisionDTO);
    }

    /**
     * {@code PUT  /remisions/:id} : Updates an existing remision.
     *
     * @param id the id of the remisionDTO to save.
     * @param remisionDTO the remisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remisionDTO,
     * or with status {@code 400 (Bad Request)} if the remisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RemisionDTO> updateRemision(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RemisionDTO remisionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Remision : {}, {}", id, remisionDTO);
        if (remisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        remisionDTO = remisionService.update(remisionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remisionDTO.getId().toString()))
            .body(remisionDTO);
    }

    /**
     * {@code PATCH  /remisions/:id} : Partial updates given fields of an existing remision, field will ignore if it is null
     *
     * @param id the id of the remisionDTO to save.
     * @param remisionDTO the remisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remisionDTO,
     * or with status {@code 400 (Bad Request)} if the remisionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the remisionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the remisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemisionDTO> partialUpdateRemision(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RemisionDTO remisionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Remision : {}, {}", id, remisionDTO);
        if (remisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemisionDTO> result = remisionService.partialUpdate(remisionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remisionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remisions} : get all the Remisions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Remisions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RemisionDTO>> getAllRemisions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Remisions");
        Page<RemisionDTO> page;
        if (eagerload) {
            page = remisionService.findAllWithEagerRelationships(pageable);
        } else {
            page = remisionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remisions/:id} : get the "id" remision.
     *
     * @param id the id of the remisionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remisionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RemisionDTO> getRemision(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Remision : {}", id);
        Optional<RemisionDTO> remisionDTO = remisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remisionDTO);
    }

    /**
     * {@code DELETE  /remisions/:id} : delete the "id" remision.
     *
     * @param id the id of the remisionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemision(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Remision : {}", id);
        remisionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
