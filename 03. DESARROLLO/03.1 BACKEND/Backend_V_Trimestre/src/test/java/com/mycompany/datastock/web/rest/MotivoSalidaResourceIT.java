package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.MotivoSalidaAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.MotivoSalida;
import com.mycompany.datastock.repository.MotivoSalidaRepository;
import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
import com.mycompany.datastock.service.mapper.MotivoSalidaMapper;
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
 * Integration tests for the {@link MotivoSalidaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MotivoSalidaResourceIT {

    private static final String DEFAULT_TIPO_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_MOTIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/motivo-salidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MotivoSalidaRepository motivoSalidaRepository;

    @Autowired
    private MotivoSalidaMapper motivoSalidaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotivoSalidaMockMvc;

    private MotivoSalida motivoSalida;

    private MotivoSalida insertedMotivoSalida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivoSalida createEntity() {
        return new MotivoSalida().tipoMotivo(DEFAULT_TIPO_MOTIVO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivoSalida createUpdatedEntity() {
        return new MotivoSalida().tipoMotivo(UPDATED_TIPO_MOTIVO);
    }

    @BeforeEach
    void initTest() {
        motivoSalida = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMotivoSalida != null) {
            motivoSalidaRepository.delete(insertedMotivoSalida);
            insertedMotivoSalida = null;
        }
    }

    @Test
    @Transactional
    void createMotivoSalida() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);
        var returnedMotivoSalidaDTO = om.readValue(
            restMotivoSalidaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motivoSalidaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MotivoSalidaDTO.class
        );

        // Validate the MotivoSalida in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMotivoSalida = motivoSalidaMapper.toEntity(returnedMotivoSalidaDTO);
        assertMotivoSalidaUpdatableFieldsEquals(returnedMotivoSalida, getPersistedMotivoSalida(returnedMotivoSalida));

        insertedMotivoSalida = returnedMotivoSalida;
    }

    @Test
    @Transactional
    void createMotivoSalidaWithExistingId() throws Exception {
        // Create the MotivoSalida with an existing ID
        motivoSalida.setId(1L);
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivoSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motivoSalidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoMotivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        motivoSalida.setTipoMotivo(null);

        // Create the MotivoSalida, which fails.
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        restMotivoSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motivoSalidaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMotivoSalidas() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        // Get all the motivoSalidaList
        restMotivoSalidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoSalida.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMotivo").value(hasItem(DEFAULT_TIPO_MOTIVO)));
    }

    @Test
    @Transactional
    void getMotivoSalida() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        // Get the motivoSalida
        restMotivoSalidaMockMvc
            .perform(get(ENTITY_API_URL_ID, motivoSalida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motivoSalida.getId().intValue()))
            .andExpect(jsonPath("$.tipoMotivo").value(DEFAULT_TIPO_MOTIVO));
    }

    @Test
    @Transactional
    void getNonExistingMotivoSalida() throws Exception {
        // Get the motivoSalida
        restMotivoSalidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMotivoSalida() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motivoSalida
        MotivoSalida updatedMotivoSalida = motivoSalidaRepository.findById(motivoSalida.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMotivoSalida are not directly saved in db
        em.detach(updatedMotivoSalida);
        updatedMotivoSalida.tipoMotivo(UPDATED_TIPO_MOTIVO);
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(updatedMotivoSalida);

        restMotivoSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motivoSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(motivoSalidaDTO))
            )
            .andExpect(status().isOk());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMotivoSalidaToMatchAllProperties(updatedMotivoSalida);
    }

    @Test
    @Transactional
    void putNonExistingMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motivoSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(motivoSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(motivoSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motivoSalidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMotivoSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motivoSalida using partial update
        MotivoSalida partialUpdatedMotivoSalida = new MotivoSalida();
        partialUpdatedMotivoSalida.setId(motivoSalida.getId());

        partialUpdatedMotivoSalida.tipoMotivo(UPDATED_TIPO_MOTIVO);

        restMotivoSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotivoSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMotivoSalida))
            )
            .andExpect(status().isOk());

        // Validate the MotivoSalida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMotivoSalidaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMotivoSalida, motivoSalida),
            getPersistedMotivoSalida(motivoSalida)
        );
    }

    @Test
    @Transactional
    void fullUpdateMotivoSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motivoSalida using partial update
        MotivoSalida partialUpdatedMotivoSalida = new MotivoSalida();
        partialUpdatedMotivoSalida.setId(motivoSalida.getId());

        partialUpdatedMotivoSalida.tipoMotivo(UPDATED_TIPO_MOTIVO);

        restMotivoSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotivoSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMotivoSalida))
            )
            .andExpect(status().isOk());

        // Validate the MotivoSalida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMotivoSalidaUpdatableFieldsEquals(partialUpdatedMotivoSalida, getPersistedMotivoSalida(partialUpdatedMotivoSalida));
    }

    @Test
    @Transactional
    void patchNonExistingMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, motivoSalidaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(motivoSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(motivoSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMotivoSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motivoSalida.setId(longCount.incrementAndGet());

        // Create the MotivoSalida
        MotivoSalidaDTO motivoSalidaDTO = motivoSalidaMapper.toDto(motivoSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivoSalidaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(motivoSalidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotivoSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMotivoSalida() throws Exception {
        // Initialize the database
        insertedMotivoSalida = motivoSalidaRepository.saveAndFlush(motivoSalida);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the motivoSalida
        restMotivoSalidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, motivoSalida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return motivoSalidaRepository.count();
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

    protected MotivoSalida getPersistedMotivoSalida(MotivoSalida motivoSalida) {
        return motivoSalidaRepository.findById(motivoSalida.getId()).orElseThrow();
    }

    protected void assertPersistedMotivoSalidaToMatchAllProperties(MotivoSalida expectedMotivoSalida) {
        assertMotivoSalidaAllPropertiesEquals(expectedMotivoSalida, getPersistedMotivoSalida(expectedMotivoSalida));
    }

    protected void assertPersistedMotivoSalidaToMatchUpdatableProperties(MotivoSalida expectedMotivoSalida) {
        assertMotivoSalidaAllUpdatablePropertiesEquals(expectedMotivoSalida, getPersistedMotivoSalida(expectedMotivoSalida));
    }
}
