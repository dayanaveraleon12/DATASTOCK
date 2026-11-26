package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.SitioAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.SitioRepository;
import com.mycompany.datastock.service.dto.SitioDTO;
import com.mycompany.datastock.service.mapper.SitioMapper;
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
 * Integration tests for the {@link SitioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SitioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sitios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SitioRepository sitioRepository;

    @Autowired
    private SitioMapper sitioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSitioMockMvc;

    private Sitio sitio;

    private Sitio insertedSitio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sitio createEntity() {
        return new Sitio().nombre(DEFAULT_NOMBRE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sitio createUpdatedEntity() {
        return new Sitio().nombre(UPDATED_NOMBRE);
    }

    @BeforeEach
    void initTest() {
        sitio = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSitio != null) {
            sitioRepository.delete(insertedSitio);
            insertedSitio = null;
        }
    }

    @Test
    @Transactional
    void createSitio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);
        var returnedSitioDTO = om.readValue(
            restSitioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SitioDTO.class
        );

        // Validate the Sitio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSitio = sitioMapper.toEntity(returnedSitioDTO);
        assertSitioUpdatableFieldsEquals(returnedSitio, getPersistedSitio(returnedSitio));

        insertedSitio = returnedSitio;
    }

    @Test
    @Transactional
    void createSitioWithExistingId() throws Exception {
        // Create the Sitio with an existing ID
        sitio.setId(1L);
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sitio.setNombre(null);

        // Create the Sitio, which fails.
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        restSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSitios() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        // Get all the sitioList
        restSitioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sitio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getSitio() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        // Get the sitio
        restSitioMockMvc
            .perform(get(ENTITY_API_URL_ID, sitio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sitio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingSitio() throws Exception {
        // Get the sitio
        restSitioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSitio() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sitio
        Sitio updatedSitio = sitioRepository.findById(sitio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSitio are not directly saved in db
        em.detach(updatedSitio);
        updatedSitio.nombre(UPDATED_NOMBRE);
        SitioDTO sitioDTO = sitioMapper.toDto(updatedSitio);

        restSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sitioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSitioToMatchAllProperties(updatedSitio);
    }

    @Test
    @Transactional
    void putNonExistingSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sitioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSitioWithPatch() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sitio using partial update
        Sitio partialUpdatedSitio = new Sitio();
        partialUpdatedSitio.setId(sitio.getId());

        restSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSitio))
            )
            .andExpect(status().isOk());

        // Validate the Sitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSitioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSitio, sitio), getPersistedSitio(sitio));
    }

    @Test
    @Transactional
    void fullUpdateSitioWithPatch() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sitio using partial update
        Sitio partialUpdatedSitio = new Sitio();
        partialUpdatedSitio.setId(sitio.getId());

        partialUpdatedSitio.nombre(UPDATED_NOMBRE);

        restSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSitio))
            )
            .andExpect(status().isOk());

        // Validate the Sitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSitioUpdatableFieldsEquals(partialUpdatedSitio, getPersistedSitio(partialUpdatedSitio));
    }

    @Test
    @Transactional
    void patchNonExistingSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sitioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sitio.setId(longCount.incrementAndGet());

        // Create the Sitio
        SitioDTO sitioDTO = sitioMapper.toDto(sitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSitioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSitio() throws Exception {
        // Initialize the database
        insertedSitio = sitioRepository.saveAndFlush(sitio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sitio
        restSitioMockMvc
            .perform(delete(ENTITY_API_URL_ID, sitio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sitioRepository.count();
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

    protected Sitio getPersistedSitio(Sitio sitio) {
        return sitioRepository.findById(sitio.getId()).orElseThrow();
    }

    protected void assertPersistedSitioToMatchAllProperties(Sitio expectedSitio) {
        assertSitioAllPropertiesEquals(expectedSitio, getPersistedSitio(expectedSitio));
    }

    protected void assertPersistedSitioToMatchUpdatableProperties(Sitio expectedSitio) {
        assertSitioAllUpdatablePropertiesEquals(expectedSitio, getPersistedSitio(expectedSitio));
    }
}
