package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.EstadoProductoAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.EstadoProducto;
import com.mycompany.datastock.repository.EstadoProductoRepository;
import com.mycompany.datastock.service.dto.EstadoProductoDTO;
import com.mycompany.datastock.service.mapper.EstadoProductoMapper;
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
 * Integration tests for the {@link EstadoProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoProductoResourceIT {

    private static final String DEFAULT_TIPO_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EstadoProductoRepository estadoProductoRepository;

    @Autowired
    private EstadoProductoMapper estadoProductoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoProductoMockMvc;

    private EstadoProducto estadoProducto;

    private EstadoProducto insertedEstadoProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoProducto createEntity() {
        return new EstadoProducto().tipoEstado(DEFAULT_TIPO_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoProducto createUpdatedEntity() {
        return new EstadoProducto().tipoEstado(UPDATED_TIPO_ESTADO);
    }

    @BeforeEach
    void initTest() {
        estadoProducto = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEstadoProducto != null) {
            estadoProductoRepository.delete(insertedEstadoProducto);
            insertedEstadoProducto = null;
        }
    }

    @Test
    @Transactional
    void createEstadoProducto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);
        var returnedEstadoProductoDTO = om.readValue(
            restEstadoProductoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadoProductoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EstadoProductoDTO.class
        );

        // Validate the EstadoProducto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEstadoProducto = estadoProductoMapper.toEntity(returnedEstadoProductoDTO);
        assertEstadoProductoUpdatableFieldsEquals(returnedEstadoProducto, getPersistedEstadoProducto(returnedEstadoProducto));

        insertedEstadoProducto = returnedEstadoProducto;
    }

    @Test
    @Transactional
    void createEstadoProductoWithExistingId() throws Exception {
        // Create the EstadoProducto with an existing ID
        estadoProducto.setId(1L);
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        estadoProducto.setTipoEstado(null);

        // Create the EstadoProducto, which fails.
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        restEstadoProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadoProductoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstadoProductos() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        // Get all the estadoProductoList
        restEstadoProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEstado").value(hasItem(DEFAULT_TIPO_ESTADO)));
    }

    @Test
    @Transactional
    void getEstadoProducto() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        // Get the estadoProducto
        restEstadoProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoProducto.getId().intValue()))
            .andExpect(jsonPath("$.tipoEstado").value(DEFAULT_TIPO_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingEstadoProducto() throws Exception {
        // Get the estadoProducto
        restEstadoProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoProducto() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadoProducto
        EstadoProducto updatedEstadoProducto = estadoProductoRepository.findById(estadoProducto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstadoProducto are not directly saved in db
        em.detach(updatedEstadoProducto);
        updatedEstadoProducto.tipoEstado(UPDATED_TIPO_ESTADO);
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(updatedEstadoProducto);

        restEstadoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadoProductoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEstadoProductoToMatchAllProperties(updatedEstadoProducto);
    }

    @Test
    @Transactional
    void putNonExistingEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadoProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadoProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadoProductoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoProductoWithPatch() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadoProducto using partial update
        EstadoProducto partialUpdatedEstadoProducto = new EstadoProducto();
        partialUpdatedEstadoProducto.setId(estadoProducto.getId());

        partialUpdatedEstadoProducto.tipoEstado(UPDATED_TIPO_ESTADO);

        restEstadoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstadoProducto))
            )
            .andExpect(status().isOk());

        // Validate the EstadoProducto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadoProductoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEstadoProducto, estadoProducto),
            getPersistedEstadoProducto(estadoProducto)
        );
    }

    @Test
    @Transactional
    void fullUpdateEstadoProductoWithPatch() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadoProducto using partial update
        EstadoProducto partialUpdatedEstadoProducto = new EstadoProducto();
        partialUpdatedEstadoProducto.setId(estadoProducto.getId());

        partialUpdatedEstadoProducto.tipoEstado(UPDATED_TIPO_ESTADO);

        restEstadoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstadoProducto))
            )
            .andExpect(status().isOk());

        // Validate the EstadoProducto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadoProductoUpdatableFieldsEquals(partialUpdatedEstadoProducto, getPersistedEstadoProducto(partialUpdatedEstadoProducto));
    }

    @Test
    @Transactional
    void patchNonExistingEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoProductoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estadoProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estadoProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadoProducto.setId(longCount.incrementAndGet());

        // Create the EstadoProducto
        EstadoProductoDTO estadoProductoDTO = estadoProductoMapper.toDto(estadoProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoProductoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(estadoProductoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoProducto() throws Exception {
        // Initialize the database
        insertedEstadoProducto = estadoProductoRepository.saveAndFlush(estadoProducto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the estadoProducto
        restEstadoProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return estadoProductoRepository.count();
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

    protected EstadoProducto getPersistedEstadoProducto(EstadoProducto estadoProducto) {
        return estadoProductoRepository.findById(estadoProducto.getId()).orElseThrow();
    }

    protected void assertPersistedEstadoProductoToMatchAllProperties(EstadoProducto expectedEstadoProducto) {
        assertEstadoProductoAllPropertiesEquals(expectedEstadoProducto, getPersistedEstadoProducto(expectedEstadoProducto));
    }

    protected void assertPersistedEstadoProductoToMatchUpdatableProperties(EstadoProducto expectedEstadoProducto) {
        assertEstadoProductoAllUpdatablePropertiesEquals(expectedEstadoProducto, getPersistedEstadoProducto(expectedEstadoProducto));
    }
}
