package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.PresentacionRepository;
import com.mycompany.datastock.service.PresentacionService;
import com.mycompany.datastock.service.dto.PresentacionDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.Presentacion}.
 */
@RestController
@RequestMapping("/api/presentacions")
public class PresentacionResource {

    private static final Logger LOG = LoggerFactory.getLogger(PresentacionResource.class);

    private static final String ENTITY_NAME = "presentacion";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final PresentacionService presentacionService;

    private final PresentacionRepository presentacionRepository;

    public PresentacionResource(PresentacionService presentacionService, PresentacionRepository presentacionRepository) {
        this.presentacionService = presentacionService;
        this.presentacionRepository = presentacionRepository;
    }

    /**
     * {@code POST  /presentacions} : Create a new presentacion.
     *
     * @param presentacionDTO the presentacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presentacionDTO, or with status {@code 400 (Bad Request)} if the presentacion already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PresentacionDTO> createPresentacion(@Valid @RequestBody PresentacionDTO presentacionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Presentacion : {}", presentacionDTO);
        if (presentacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new presentacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        presentacionDTO = presentacionService.save(presentacionDTO);
        return ResponseEntity.created(new URI("/api/presentacions/" + presentacionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, presentacionDTO.getId().toString()))
            .body(presentacionDTO);
    }

    /**
     * {@code PUT  /presentacions/:id} : Updates an existing presentacion.
     *
     * @param id the id of the presentacionDTO to save.
     * @param presentacionDTO the presentacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentacionDTO,
     * or with status {@code 400 (Bad Request)} if the presentacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presentacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PresentacionDTO> updatePresentacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PresentacionDTO presentacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Presentacion : {}, {}", id, presentacionDTO);
        if (presentacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, presentacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!presentacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        presentacionDTO = presentacionService.update(presentacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, presentacionDTO.getId().toString()))
            .body(presentacionDTO);
    }

    /**
     * {@code PATCH  /presentacions/:id} : Partial updates given fields of an existing presentacion, field will ignore if it is null
     *
     * @param id the id of the presentacionDTO to save.
     * @param presentacionDTO the presentacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentacionDTO,
     * or with status {@code 400 (Bad Request)} if the presentacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the presentacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the presentacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PresentacionDTO> partialUpdatePresentacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PresentacionDTO presentacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Presentacion : {}, {}", id, presentacionDTO);
        if (presentacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, presentacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!presentacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PresentacionDTO> result = presentacionService.partialUpdate(presentacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, presentacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /presentacions} : get all the Presentacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Presentacions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PresentacionDTO>> getAllPresentacions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Presentacions");
        Page<PresentacionDTO> page = presentacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presentacions/:id} : get the "id" presentacion.
     *
     * @param id the id of the presentacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presentacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PresentacionDTO> getPresentacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Presentacion : {}", id);
        Optional<PresentacionDTO> presentacionDTO = presentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presentacionDTO);
    }

    /**
     * {@code DELETE  /presentacions/:id} : delete the "id" presentacion.
     *
     * @param id the id of the presentacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePresentacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Presentacion : {}", id);
        presentacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
