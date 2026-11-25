package com.mycompany.datastock.web.rest;

import com.mycompany.datastock.repository.MotivoSalidaRepository;
import com.mycompany.datastock.service.MotivoSalidaService;
import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
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
 * REST controller for managing {@link com.mycompany.datastock.domain.MotivoSalida}.
 */
@RestController
@RequestMapping("/api/motivo-salidas")
public class MotivoSalidaResource {

    private static final Logger LOG = LoggerFactory.getLogger(MotivoSalidaResource.class);

    private static final String ENTITY_NAME = "motivoSalida";

    @Value("${jhipster.clientApp.name:datastock}")
    private String applicationName;

    private final MotivoSalidaService motivoSalidaService;

    private final MotivoSalidaRepository motivoSalidaRepository;

    public MotivoSalidaResource(MotivoSalidaService motivoSalidaService, MotivoSalidaRepository motivoSalidaRepository) {
        this.motivoSalidaService = motivoSalidaService;
        this.motivoSalidaRepository = motivoSalidaRepository;
    }

    /**
     * {@code POST  /motivo-salidas} : Create a new motivoSalida.
     *
     * @param motivoSalidaDTO the motivoSalidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivoSalidaDTO, or with status {@code 400 (Bad Request)} if the motivoSalida already has an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MotivoSalidaDTO> createMotivoSalida(@Valid @RequestBody MotivoSalidaDTO motivoSalidaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MotivoSalida : {}", motivoSalidaDTO);
        if (motivoSalidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new motivoSalida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        motivoSalidaDTO = motivoSalidaService.save(motivoSalidaDTO);
        return ResponseEntity.created(new URI("/api/motivo-salidas/" + motivoSalidaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, motivoSalidaDTO.getId().toString()))
            .body(motivoSalidaDTO);
    }

    /**
     * {@code PUT  /motivo-salidas/:id} : Updates an existing motivoSalida.
     *
     * @param id the id of the motivoSalidaDTO to save.
     * @param motivoSalidaDTO the motivoSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivoSalidaDTO,
     * or with status {@code 400 (Bad Request)} if the motivoSalidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivoSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MotivoSalidaDTO> updateMotivoSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MotivoSalidaDTO motivoSalidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MotivoSalida : {}, {}", id, motivoSalidaDTO);
        if (motivoSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivoSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motivoSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        motivoSalidaDTO = motivoSalidaService.update(motivoSalidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivoSalidaDTO.getId().toString()))
            .body(motivoSalidaDTO);
    }

    /**
     * {@code PATCH  /motivo-salidas/:id} : Partial updates given fields of an existing motivoSalida, field will ignore if it is null
     *
     * @param id the id of the motivoSalidaDTO to save.
     * @param motivoSalidaDTO the motivoSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivoSalidaDTO,
     * or with status {@code 400 (Bad Request)} if the motivoSalidaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the motivoSalidaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the motivoSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MotivoSalidaDTO> partialUpdateMotivoSalida(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MotivoSalidaDTO motivoSalidaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update MotivoSalida : {}, {}", id, motivoSalidaDTO);
        if (motivoSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivoSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motivoSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MotivoSalidaDTO> result = motivoSalidaService.partialUpdate(motivoSalidaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivoSalidaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /motivo-salidas} : get all the Motivo Salidas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Motivo Salidas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MotivoSalidaDTO>> getAllMotivoSalidas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MotivoSalidas");
        Page<MotivoSalidaDTO> page = motivoSalidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motivo-salidas/:id} : get the "id" motivoSalida.
     *
     * @param id the id of the motivoSalidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivoSalidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MotivoSalidaDTO> getMotivoSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MotivoSalida : {}", id);
        Optional<MotivoSalidaDTO> motivoSalidaDTO = motivoSalidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motivoSalidaDTO);
    }

    /**
     * {@code DELETE  /motivo-salidas/:id} : delete the "id" motivoSalida.
     *
     * @param id the id of the motivoSalidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotivoSalida(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MotivoSalida : {}", id);
        motivoSalidaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
