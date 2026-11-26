package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.TrasladoProductoSitioAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Traslado;
import com.mycompany.datastock.domain.TrasladoProductoSitio;
import com.mycompany.datastock.repository.TrasladoProductoSitioRepository;
import com.mycompany.datastock.service.dto.TrasladoProductoSitioDTO;
import com.mycompany.datastock.service.mapper.TrasladoProductoSitioMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

/**
 * Integration tests for the {@link TrasladoProductoSitioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrasladoProductoSitioResourceIT {

    private static final Integer DEFAULT_CANTIDAD_TRASLADO = 0;
    private static final Integer UPDATED_CANTIDAD_TRASLADO = 1;

    private static final String ENTITY_API_URL = "/api/traslado-producto-sitios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrasladoProductoSitioRepository trasladoProductoSitioRepository;

    @Autowired
    private TrasladoProductoSitioMapper trasladoProductoSitioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrasladoProductoSitioMockMvc;

    private TrasladoProductoSitio trasladoProductoSitio;

    private TrasladoProductoSitio insertedTrasladoProductoSitio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrasladoProductoSitio createEntity(EntityManager em) {
        TrasladoProductoSitio trasladoProductoSitio = new TrasladoProductoSitio().cantidadTraslado(DEFAULT_CANTIDAD_TRASLADO);
        // Add required entity
        Traslado traslado;
        if (TestUtil.findAll(em, Traslado.class).isEmpty()) {
            traslado = TrasladoResourceIT.createEntity(em);
            em.persist(traslado);
            em.flush();
        } else {
            traslado = TestUtil.findAll(em, Traslado.class).getFirst();
        }
        trasladoProductoSitio.setTraslado(traslado);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        trasladoProductoSitio.setProductoSitio(productoSitio);
        return trasladoProductoSitio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrasladoProductoSitio createUpdatedEntity(EntityManager em) {
        TrasladoProductoSitio updatedTrasladoProductoSitio = new TrasladoProductoSitio().cantidadTraslado(UPDATED_CANTIDAD_TRASLADO);
        // Add required entity
        Traslado traslado;
        if (TestUtil.findAll(em, Traslado.class).isEmpty()) {
            traslado = TrasladoResourceIT.createUpdatedEntity(em);
            em.persist(traslado);
            em.flush();
        } else {
            traslado = TestUtil.findAll(em, Traslado.class).getFirst();
        }
        updatedTrasladoProductoSitio.setTraslado(traslado);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createUpdatedEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        updatedTrasladoProductoSitio.setProductoSitio(productoSitio);
        return updatedTrasladoProductoSitio;
    }

    @BeforeEach
    void initTest() {
        trasladoProductoSitio = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTrasladoProductoSitio != null) {
            trasladoProductoSitioRepository.delete(insertedTrasladoProductoSitio);
            insertedTrasladoProductoSitio = null;
        }
    }

    @Test
    @Transactional
    void createTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);
        var returnedTrasladoProductoSitioDTO = om.readValue(
            restTrasladoProductoSitioMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoProductoSitioDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrasladoProductoSitioDTO.class
        );

        // Validate the TrasladoProductoSitio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTrasladoProductoSitio = trasladoProductoSitioMapper.toEntity(returnedTrasladoProductoSitioDTO);
        assertTrasladoProductoSitioUpdatableFieldsEquals(
            returnedTrasladoProductoSitio,
            getPersistedTrasladoProductoSitio(returnedTrasladoProductoSitio)
        );

        insertedTrasladoProductoSitio = returnedTrasladoProductoSitio;
    }

    @Test
    @Transactional
    void createTrasladoProductoSitioWithExistingId() throws Exception {
        // Create the TrasladoProductoSitio with an existing ID
        trasladoProductoSitio.setId(1L);
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrasladoProductoSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoProductoSitioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadTrasladoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trasladoProductoSitio.setCantidadTraslado(null);

        // Create the TrasladoProductoSitio, which fails.
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        restTrasladoProductoSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoProductoSitioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrasladoProductoSitios() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        // Get all the trasladoProductoSitioList
        restTrasladoProductoSitioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trasladoProductoSitio.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadTraslado").value(hasItem(DEFAULT_CANTIDAD_TRASLADO)));
    }

    @Test
    @Transactional
    void getTrasladoProductoSitio() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        // Get the trasladoProductoSitio
        restTrasladoProductoSitioMockMvc
            .perform(get(ENTITY_API_URL_ID, trasladoProductoSitio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trasladoProductoSitio.getId().intValue()))
            .andExpect(jsonPath("$.cantidadTraslado").value(DEFAULT_CANTIDAD_TRASLADO));
    }

    @Test
    @Transactional
    void getNonExistingTrasladoProductoSitio() throws Exception {
        // Get the trasladoProductoSitio
        restTrasladoProductoSitioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrasladoProductoSitio() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trasladoProductoSitio
        TrasladoProductoSitio updatedTrasladoProductoSitio = trasladoProductoSitioRepository
            .findById(trasladoProductoSitio.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTrasladoProductoSitio are not directly saved in db
        em.detach(updatedTrasladoProductoSitio);
        updatedTrasladoProductoSitio.cantidadTraslado(UPDATED_CANTIDAD_TRASLADO);
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(updatedTrasladoProductoSitio);

        restTrasladoProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trasladoProductoSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrasladoProductoSitioToMatchAllProperties(updatedTrasladoProductoSitio);
    }

    @Test
    @Transactional
    void putNonExistingTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trasladoProductoSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoProductoSitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrasladoProductoSitioWithPatch() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trasladoProductoSitio using partial update
        TrasladoProductoSitio partialUpdatedTrasladoProductoSitio = new TrasladoProductoSitio();
        partialUpdatedTrasladoProductoSitio.setId(trasladoProductoSitio.getId());

        partialUpdatedTrasladoProductoSitio.cantidadTraslado(UPDATED_CANTIDAD_TRASLADO);

        restTrasladoProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrasladoProductoSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrasladoProductoSitio))
            )
            .andExpect(status().isOk());

        // Validate the TrasladoProductoSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrasladoProductoSitioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrasladoProductoSitio, trasladoProductoSitio),
            getPersistedTrasladoProductoSitio(trasladoProductoSitio)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrasladoProductoSitioWithPatch() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trasladoProductoSitio using partial update
        TrasladoProductoSitio partialUpdatedTrasladoProductoSitio = new TrasladoProductoSitio();
        partialUpdatedTrasladoProductoSitio.setId(trasladoProductoSitio.getId());

        partialUpdatedTrasladoProductoSitio.cantidadTraslado(UPDATED_CANTIDAD_TRASLADO);

        restTrasladoProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrasladoProductoSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrasladoProductoSitio))
            )
            .andExpect(status().isOk());

        // Validate the TrasladoProductoSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrasladoProductoSitioUpdatableFieldsEquals(
            partialUpdatedTrasladoProductoSitio,
            getPersistedTrasladoProductoSitio(partialUpdatedTrasladoProductoSitio)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trasladoProductoSitioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrasladoProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trasladoProductoSitio.setId(longCount.incrementAndGet());

        // Create the TrasladoProductoSitio
        TrasladoProductoSitioDTO trasladoProductoSitioDTO = trasladoProductoSitioMapper.toDto(trasladoProductoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trasladoProductoSitioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrasladoProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrasladoProductoSitio() throws Exception {
        // Initialize the database
        insertedTrasladoProductoSitio = trasladoProductoSitioRepository.saveAndFlush(trasladoProductoSitio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trasladoProductoSitio
        restTrasladoProductoSitioMockMvc
            .perform(delete(ENTITY_API_URL_ID, trasladoProductoSitio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trasladoProductoSitioRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TrasladoProductoSitio getPersistedTrasladoProductoSitio(TrasladoProductoSitio trasladoProductoSitio) {
        return trasladoProductoSitioRepository.findById(trasladoProductoSitio.getId()).orElseThrow();
    }

    protected void assertPersistedTrasladoProductoSitioToMatchAllProperties(TrasladoProductoSitio expectedTrasladoProductoSitio) {
        assertTrasladoProductoSitioAllPropertiesEquals(
            expectedTrasladoProductoSitio,
            getPersistedTrasladoProductoSitio(expectedTrasladoProductoSitio)
        );
    }

    protected void assertPersistedTrasladoProductoSitioToMatchUpdatableProperties(TrasladoProductoSitio expectedTrasladoProductoSitio) {
        assertTrasladoProductoSitioAllUpdatablePropertiesEquals(
            expectedTrasladoProductoSitio,
            getPersistedTrasladoProductoSitio(expectedTrasladoProductoSitio)
        );
    }
}
