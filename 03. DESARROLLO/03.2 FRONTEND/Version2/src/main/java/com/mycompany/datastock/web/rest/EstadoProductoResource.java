package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.EstadoProductoRepository;
import com.mycompany.datastock.service.EstadoProductoService;
import com.mycompany.datastock.service.dto.EstadoProductoDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.EstadoProducto}.
 */
@RestController
@RequestMapping("/api/estado-productos")
public class EstadoProductoResource {

    private static final Logger LOG = LoggerFactory.getLogger(EstadoProductoResource.class);

    private static final String ENTITY_NAME = "estadoProducto";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final EstadoProductoService estadoProductoService;

    private final EstadoProductoRepository estadoProductoRepository;

    public EstadoProductoResource(EstadoProductoService estadoProductoService, EstadoProductoRepository estadoProductoRepository) {
        this.estadoProductoService = estadoProductoService;
        this.estadoProductoRepository = estadoProductoRepository;
    }

    /**
     * {@code POST  /estado-productos} : Create a new estadoProducto.
     *
     * @param estadoProductoDTO the estadoProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoProductoDTO, or with status {@code 400 (Bad Request)} if the estadoProducto already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EstadoProductoDTO> createEstadoProducto(@Valid @RequestBody EstadoProductoDTO estadoProductoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EstadoProducto : {}", estadoProductoDTO);
        if (estadoProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        estadoProductoDTO = estadoProductoService.save(estadoProductoDTO);
        return ResponseEntity.created(new URI("/api/estado-productos/" + estadoProductoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, estadoProductoDTO.getId().toString()))
            .body(estadoProductoDTO);
    }

    /**
     * {@code PUT  /estado-productos/:id} : Updates an existing estadoProducto.
     *
     * @param id the id of the estadoProductoDTO to save.
     * @param estadoProductoDTO the estadoProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoProductoDTO,
     * or with status {@code 400 (Bad Request)} if the estadoProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstadoProductoDTO> updateEstadoProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstadoProductoDTO estadoProductoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EstadoProducto : {}, {}", id, estadoProductoDTO);
        if (estadoProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        estadoProductoDTO = estadoProductoService.update(estadoProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoProductoDTO.getId().toString()))
            .body(estadoProductoDTO);
    }

    /**
     * {@code PATCH  /estado-productos/:id} : Partial updates given fields of an existing estadoProducto, field will ignore if it is null
     *
     * @param id the id of the estadoProductoDTO to save.
     * @param estadoProductoDTO the estadoProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoProductoDTO,
     * or with status {@code 400 (Bad Request)} if the estadoProductoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadoProductoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoProductoDTO> partialUpdateEstadoProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstadoProductoDTO estadoProductoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update EstadoProducto : {}, {}", id, estadoProductoDTO);
        if (estadoProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoProductoDTO> result = estadoProductoService.partialUpdate(estadoProductoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoProductoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-productos} : get all the Estado Productos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Estado Productos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EstadoProductoDTO>> getAllEstadoProductos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of EstadoProductos");
        Page<EstadoProductoDTO> page = estadoProductoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-productos/:id} : get the "id" estadoProducto.
     *
     * @param id the id of the estadoProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstadoProductoDTO> getEstadoProducto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EstadoProducto : {}", id);
        Optional<EstadoProductoDTO> estadoProductoDTO = estadoProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoProductoDTO);
    }

    /**
     * {@code DELETE  /estado-productos/:id} : delete the "id" estadoProducto.
     *
     * @param id the id of the estadoProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoProducto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EstadoProducto : {}", id);
        estadoProductoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
