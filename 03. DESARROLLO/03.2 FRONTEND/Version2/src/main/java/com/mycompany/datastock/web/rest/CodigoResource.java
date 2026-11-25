package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.CodigoRepository;
import com.mycompany.datastock.service.CodigoService;
import com.mycompany.datastock.service.dto.CodigoDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.Codigo}.
 */
@RestController
@RequestMapping("/api/codigos")
public class CodigoResource {

    private static final Logger LOG = LoggerFactory.getLogger(CodigoResource.class);

    private static final String ENTITY_NAME = "codigo";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final CodigoService codigoService;

    private final CodigoRepository codigoRepository;

    public CodigoResource(CodigoService codigoService, CodigoRepository codigoRepository) {
        this.codigoService = codigoService;
        this.codigoRepository = codigoRepository;
    }

    /**
     * {@code POST  /codigos} : Create a new codigo.
     *
     * @param codigoDTO the codigoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codigoDTO, or with status {@code 400 (Bad Request)} if the codigo already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CodigoDTO> createCodigo(@Valid @RequestBody CodigoDTO codigoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Codigo : {}", codigoDTO);
        if (codigoDTO.getId() != null) {
            throw new BadRequestAlertException("A new codigo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        codigoDTO = codigoService.save(codigoDTO);
        return ResponseEntity.created(new URI("/api/codigos/" + codigoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, codigoDTO.getId().toString()))
            .body(codigoDTO);
    }

    /**
     * {@code PUT  /codigos/:id} : Updates an existing codigo.
     *
     * @param id the id of the codigoDTO to save.
     * @param codigoDTO the codigoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codigoDTO,
     * or with status {@code 400 (Bad Request)} if the codigoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codigoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodigoDTO> updateCodigo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CodigoDTO codigoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Codigo : {}, {}", id, codigoDTO);
        if (codigoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codigoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codigoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        codigoDTO = codigoService.update(codigoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codigoDTO.getId().toString()))
            .body(codigoDTO);
    }

    /**
     * {@code PATCH  /codigos/:id} : Partial updates given fields of an existing codigo, field will ignore if it is null
     *
     * @param id the id of the codigoDTO to save.
     * @param codigoDTO the codigoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codigoDTO,
     * or with status {@code 400 (Bad Request)} if the codigoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the codigoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the codigoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodigoDTO> partialUpdateCodigo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CodigoDTO codigoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Codigo : {}, {}", id, codigoDTO);
        if (codigoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codigoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codigoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodigoDTO> result = codigoService.partialUpdate(codigoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codigoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /codigos} : get all the Codigos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Codigos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CodigoDTO>> getAllCodigos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Codigos");
        Page<CodigoDTO> page = codigoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /codigos/:id} : get the "id" codigo.
     *
     * @param id the id of the codigoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codigoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodigoDTO> getCodigo(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Codigo : {}", id);
        Optional<CodigoDTO> codigoDTO = codigoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codigoDTO);
    }

    /**
     * {@code DELETE  /codigos/:id} : delete the "id" codigo.
     *
     * @param id the id of the codigoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodigo(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Codigo : {}", id);
        codigoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
