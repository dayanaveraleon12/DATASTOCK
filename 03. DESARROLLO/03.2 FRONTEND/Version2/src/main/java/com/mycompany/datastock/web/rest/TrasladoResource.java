package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.TrasladoRepository;
import com.mycompany.datastock.service.TrasladoService;
import com.mycompany.datastock.service.dto.TrasladoDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.Traslado}.
 */
@RestController
@RequestMapping("/api/traslados")
public class TrasladoResource {

    private static final Logger LOG = LoggerFactory.getLogger(TrasladoResource.class);

    private static final String ENTITY_NAME = "traslado";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final TrasladoService trasladoService;

    private final TrasladoRepository trasladoRepository;

    public TrasladoResource(TrasladoService trasladoService, TrasladoRepository trasladoRepository) {
        this.trasladoService = trasladoService;
        this.trasladoRepository = trasladoRepository;
    }

    /**
     * {@code POST  /traslados} : Create a new traslado.
     *
     * @param trasladoDTO the trasladoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trasladoDTO, or with status {@code 400 (Bad Request)} if the traslado already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TrasladoDTO> createTraslado(@Valid @RequestBody TrasladoDTO trasladoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Traslado : {}", trasladoDTO);
        if (trasladoDTO.getId() != null) {
            throw new BadRequestAlertException("A new traslado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trasladoDTO = trasladoService.save(trasladoDTO);
        return ResponseEntity.created(new URI("/api/traslados/" + trasladoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trasladoDTO.getId().toString()))
            .body(trasladoDTO);
    }

    /**
     * {@code PUT  /traslados/:id} : Updates an existing traslado.
     *
     * @param id the id of the trasladoDTO to save.
     * @param trasladoDTO the trasladoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trasladoDTO,
     * or with status {@code 400 (Bad Request)} if the trasladoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trasladoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrasladoDTO> updateTraslado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrasladoDTO trasladoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Traslado : {}, {}", id, trasladoDTO);
        if (trasladoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trasladoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trasladoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trasladoDTO = trasladoService.update(trasladoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trasladoDTO.getId().toString()))
            .body(trasladoDTO);
    }

    /**
     * {@code PATCH  /traslados/:id} : Partial updates given fields of an existing traslado, field will ignore if it is null
     *
     * @param id the id of the trasladoDTO to save.
     * @param trasladoDTO the trasladoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trasladoDTO,
     * or with status {@code 400 (Bad Request)} if the trasladoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trasladoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trasladoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrasladoDTO> partialUpdateTraslado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrasladoDTO trasladoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Traslado : {}, {}", id, trasladoDTO);
        if (trasladoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trasladoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trasladoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrasladoDTO> result = trasladoService.partialUpdate(trasladoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trasladoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /traslados} : get all the Traslados.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Traslados in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TrasladoDTO>> getAllTraslados(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Traslados");
        Page<TrasladoDTO> page;
        if (eagerload) {
            page = trasladoService.findAllWithEagerRelationships(pageable);
        } else {
            page = trasladoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /traslados/:id} : get the "id" traslado.
     *
     * @param id the id of the trasladoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trasladoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrasladoDTO> getTraslado(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Traslado : {}", id);
        Optional<TrasladoDTO> trasladoDTO = trasladoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trasladoDTO);
    }

    /**
     * {@code DELETE  /traslados/:id} : delete the "id" traslado.
     *
     * @param id the id of the trasladoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraslado(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Traslado : {}", id);
        trasladoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
