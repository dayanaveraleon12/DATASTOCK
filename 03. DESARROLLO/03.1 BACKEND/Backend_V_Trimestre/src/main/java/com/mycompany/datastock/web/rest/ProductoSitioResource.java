package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.ProductoSitioRepository;
import com.mycompany.datastock.service.ProductoSitioService;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.ProductoSitio}.
 */
@RestController
@RequestMapping("/api/producto-sitios")
public class ProductoSitioResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoSitioResource.class);

    private static final String ENTITY_NAME = "productoSitio";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final ProductoSitioService productoSitioService;

    private final ProductoSitioRepository productoSitioRepository;

    public ProductoSitioResource(ProductoSitioService productoSitioService, ProductoSitioRepository productoSitioRepository) {
        this.productoSitioService = productoSitioService;
        this.productoSitioRepository = productoSitioRepository;
    }

    /**
     * {@code POST  /producto-sitios} : Create a new productoSitio.
     *
     * @param productoSitioDTO the productoSitioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoSitioDTO, or with status {@code 400 (Bad Request)} if the productoSitio already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductoSitioDTO> createProductoSitio(@Valid @RequestBody ProductoSitioDTO productoSitioDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ProductoSitio : {}", productoSitioDTO);
        if (productoSitioDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoSitio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productoSitioDTO = productoSitioService.save(productoSitioDTO);
        return ResponseEntity.created(new URI("/api/producto-sitios/" + productoSitioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, productoSitioDTO.getId().toString()))
            .body(productoSitioDTO);
    }

    /**
     * {@code PUT  /producto-sitios/:id} : Updates an existing productoSitio.
     *
     * @param id the id of the productoSitioDTO to save.
     * @param productoSitioDTO the productoSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoSitioDTO,
     * or with status {@code 400 (Bad Request)} if the productoSitioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoSitioDTO> updateProductoSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoSitioDTO productoSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ProductoSitio : {}, {}", id, productoSitioDTO);
        if (productoSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productoSitioDTO = productoSitioService.update(productoSitioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productoSitioDTO.getId().toString()))
            .body(productoSitioDTO);
    }

    /**
     * {@code PATCH  /producto-sitios/:id} : Partial updates given fields of an existing productoSitio, field will ignore if it is null
     *
     * @param id the id of the productoSitioDTO to save.
     * @param productoSitioDTO the productoSitioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoSitioDTO,
     * or with status {@code 400 (Bad Request)} if the productoSitioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productoSitioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoSitioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoSitioDTO> partialUpdateProductoSitio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoSitioDTO productoSitioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update ProductoSitio : {}, {}", id, productoSitioDTO);
        if (productoSitioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoSitioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoSitioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoSitioDTO> result = productoSitioService.partialUpdate(productoSitioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productoSitioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-sitios} : get all the Producto Sitios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Producto Sitios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProductoSitioDTO>> getAllProductoSitios(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of ProductoSitios");
        Page<ProductoSitioDTO> page;
        if (eagerload) {
            page = productoSitioService.findAllWithEagerRelationships(pageable);
        } else {
            page = productoSitioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-sitios/:id} : get the "id" productoSitio.
     *
     * @param id the id of the productoSitioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoSitioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoSitioDTO> getProductoSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ProductoSitio : {}", id);
        Optional<ProductoSitioDTO> productoSitioDTO = productoSitioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoSitioDTO);
    }

    /**
     * {@code DELETE  /producto-sitios/:id} : delete the "id" productoSitio.
     *
     * @param id the id of the productoSitioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoSitio(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ProductoSitio : {}", id);
        productoSitioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
