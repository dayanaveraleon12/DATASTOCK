package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.EvidenciaSalidaAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.EvidenciaSalida;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.EvidenciaSalidaRepository;
import com.mycompany.datastock.service.EvidenciaSalidaService;
import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
import com.mycompany.datastock.service.mapper.EvidenciaSalidaMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

/**
 * Integration tests for the {@link EvidenciaSalidaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EvidenciaSalidaResourceIT {

    private static final String DEFAULT_ARCHIVO_EXCEL = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO_EXCEL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_EXCEL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EXCEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CARGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CARGA = LocalDate.parse("2023-12-04");

    private static final String ENTITY_API_URL = "/api/evidencia-salidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EvidenciaSalidaRepository evidenciaSalidaRepository;

    @Mock
    private EvidenciaSalidaRepository evidenciaSalidaRepositoryMock;

    @Autowired
    private EvidenciaSalidaMapper evidenciaSalidaMapper;

    @Mock
    private EvidenciaSalidaService evidenciaSalidaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvidenciaSalidaMockMvc;

    private EvidenciaSalida evidenciaSalida;

    private EvidenciaSalida insertedEvidenciaSalida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvidenciaSalida createEntity(EntityManager em) {
        EvidenciaSalida evidenciaSalida = new EvidenciaSalida()
            .archivoExcel(DEFAULT_ARCHIVO_EXCEL)
            .nombreExcel(DEFAULT_NOMBRE_EXCEL)
            .fechaCarga(DEFAULT_FECHA_CARGA);
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        evidenciaSalida.setCuenta(cuenta);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        evidenciaSalida.setSitioSubida(sitio);
        return evidenciaSalida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvidenciaSalida createUpdatedEntity(EntityManager em) {
        EvidenciaSalida updatedEvidenciaSalida = new EvidenciaSalida()
            .archivoExcel(UPDATED_ARCHIVO_EXCEL)
            .nombreExcel(UPDATED_NOMBRE_EXCEL)
            .fechaCarga(UPDATED_FECHA_CARGA);
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createUpdatedEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        updatedEvidenciaSalida.setCuenta(cuenta);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createUpdatedEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        updatedEvidenciaSalida.setSitioSubida(sitio);
        return updatedEvidenciaSalida;
    }

    @BeforeEach
    void initTest() {
        evidenciaSalida = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedEvidenciaSalida != null) {
            evidenciaSalidaRepository.delete(insertedEvidenciaSalida);
            insertedEvidenciaSalida = null;
        }
    }

    @Test
    @Transactional
    void createEvidenciaSalida() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);
        var returnedEvidenciaSalidaDTO = om.readValue(
            restEvidenciaSalidaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EvidenciaSalidaDTO.class
        );

        // Validate the EvidenciaSalida in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEvidenciaSalida = evidenciaSalidaMapper.toEntity(returnedEvidenciaSalidaDTO);
        assertEvidenciaSalidaUpdatableFieldsEquals(returnedEvidenciaSalida, getPersistedEvidenciaSalida(returnedEvidenciaSalida));

        insertedEvidenciaSalida = returnedEvidenciaSalida;
    }

    @Test
    @Transactional
    void createEvidenciaSalidaWithExistingId() throws Exception {
        // Create the EvidenciaSalida with an existing ID
        evidenciaSalida.setId(1L);
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvidenciaSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkArchivoExcelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        evidenciaSalida.setArchivoExcel(null);

        // Create the EvidenciaSalida, which fails.
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        restEvidenciaSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreExcelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        evidenciaSalida.setNombreExcel(null);

        // Create the EvidenciaSalida, which fails.
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        restEvidenciaSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaCargaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        evidenciaSalida.setFechaCarga(null);

        // Create the EvidenciaSalida, which fails.
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        restEvidenciaSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvidenciaSalidas() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        // Get all the evidenciaSalidaList
        restEvidenciaSalidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evidenciaSalida.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivoExcel").value(hasItem(DEFAULT_ARCHIVO_EXCEL)))
            .andExpect(jsonPath("$.[*].nombreExcel").value(hasItem(DEFAULT_NOMBRE_EXCEL)))
            .andExpect(jsonPath("$.[*].fechaCarga").value(hasItem(DEFAULT_FECHA_CARGA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEvidenciaSalidasWithEagerRelationshipsIsEnabled() throws Exception {
        when(evidenciaSalidaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEvidenciaSalidaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(evidenciaSalidaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEvidenciaSalidasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(evidenciaSalidaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEvidenciaSalidaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(evidenciaSalidaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEvidenciaSalida() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        // Get the evidenciaSalida
        restEvidenciaSalidaMockMvc
            .perform(get(ENTITY_API_URL_ID, evidenciaSalida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evidenciaSalida.getId().intValue()))
            .andExpect(jsonPath("$.archivoExcel").value(DEFAULT_ARCHIVO_EXCEL))
            .andExpect(jsonPath("$.nombreExcel").value(DEFAULT_NOMBRE_EXCEL))
            .andExpect(jsonPath("$.fechaCarga").value(DEFAULT_FECHA_CARGA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEvidenciaSalida() throws Exception {
        // Get the evidenciaSalida
        restEvidenciaSalidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvidenciaSalida() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evidenciaSalida
        EvidenciaSalida updatedEvidenciaSalida = evidenciaSalidaRepository.findById(evidenciaSalida.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEvidenciaSalida are not directly saved in db
        em.detach(updatedEvidenciaSalida);
        updatedEvidenciaSalida.archivoExcel(UPDATED_ARCHIVO_EXCEL).nombreExcel(UPDATED_NOMBRE_EXCEL).fechaCarga(UPDATED_FECHA_CARGA);
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(updatedEvidenciaSalida);

        restEvidenciaSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evidenciaSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(evidenciaSalidaDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEvidenciaSalidaToMatchAllProperties(updatedEvidenciaSalida);
    }

    @Test
    @Transactional
    void putNonExistingEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evidenciaSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(evidenciaSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(evidenciaSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvidenciaSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evidenciaSalida using partial update
        EvidenciaSalida partialUpdatedEvidenciaSalida = new EvidenciaSalida();
        partialUpdatedEvidenciaSalida.setId(evidenciaSalida.getId());

        partialUpdatedEvidenciaSalida.archivoExcel(UPDATED_ARCHIVO_EXCEL).nombreExcel(UPDATED_NOMBRE_EXCEL);

        restEvidenciaSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvidenciaSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEvidenciaSalida))
            )
            .andExpect(status().isOk());

        // Validate the EvidenciaSalida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEvidenciaSalidaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEvidenciaSalida, evidenciaSalida),
            getPersistedEvidenciaSalida(evidenciaSalida)
        );
    }

    @Test
    @Transactional
    void fullUpdateEvidenciaSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the evidenciaSalida using partial update
        EvidenciaSalida partialUpdatedEvidenciaSalida = new EvidenciaSalida();
        partialUpdatedEvidenciaSalida.setId(evidenciaSalida.getId());

        partialUpdatedEvidenciaSalida.archivoExcel(UPDATED_ARCHIVO_EXCEL).nombreExcel(UPDATED_NOMBRE_EXCEL).fechaCarga(UPDATED_FECHA_CARGA);

        restEvidenciaSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvidenciaSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEvidenciaSalida))
            )
            .andExpect(status().isOk());

        // Validate the EvidenciaSalida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEvidenciaSalidaUpdatableFieldsEquals(
            partialUpdatedEvidenciaSalida,
            getPersistedEvidenciaSalida(partialUpdatedEvidenciaSalida)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evidenciaSalidaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(evidenciaSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(evidenciaSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvidenciaSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        evidenciaSalida.setId(longCount.incrementAndGet());

        // Create the EvidenciaSalida
        EvidenciaSalidaDTO evidenciaSalidaDTO = evidenciaSalidaMapper.toDto(evidenciaSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvidenciaSalidaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(evidenciaSalidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvidenciaSalida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvidenciaSalida() throws Exception {
        // Initialize the database
        insertedEvidenciaSalida = evidenciaSalidaRepository.saveAndFlush(evidenciaSalida);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the evidenciaSalida
        restEvidenciaSalidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, evidenciaSalida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return evidenciaSalidaRepository.count();
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

    protected EvidenciaSalida getPersistedEvidenciaSalida(EvidenciaSalida evidenciaSalida) {
        return evidenciaSalidaRepository.findById(evidenciaSalida.getId()).orElseThrow();
    }

    protected void assertPersistedEvidenciaSalidaToMatchAllProperties(EvidenciaSalida expectedEvidenciaSalida) {
        assertEvidenciaSalidaAllPropertiesEquals(expectedEvidenciaSalida, getPersistedEvidenciaSalida(expectedEvidenciaSalida));
    }

    protected void assertPersistedEvidenciaSalidaToMatchUpdatableProperties(EvidenciaSalida expectedEvidenciaSalida) {
        assertEvidenciaSalidaAllUpdatablePropertiesEquals(expectedEvidenciaSalida, getPersistedEvidenciaSalida(expectedEvidenciaSalida));
    }
}
