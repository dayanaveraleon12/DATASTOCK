package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.SalidaAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.EvidenciaSalida;
import com.mycompany.datastock.domain.MotivoSalida;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Salida;
import com.mycompany.datastock.repository.SalidaRepository;
import com.mycompany.datastock.service.SalidaService;
import com.mycompany.datastock.service.dto.SalidaDTO;
import com.mycompany.datastock.service.mapper.SalidaMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SalidaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SalidaResourceIT {

    private static final String DEFAULT_RESPALDO_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_RESPALDO_IMAGEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_A_SACAR = 1;
    private static final Integer UPDATED_CANTIDAD_A_SACAR = 2;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalidaRepository salidaRepository;

    @Mock
    private SalidaRepository salidaRepositoryMock;

    @Autowired
    private SalidaMapper salidaMapper;

    @Mock
    private SalidaService salidaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalidaMockMvc;

    private Salida salida;

    private Salida insertedSalida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salida createEntity(EntityManager em) {
        Salida salida = new Salida()
            .respaldoImagen(DEFAULT_RESPALDO_IMAGEN)
            .cantidadASacar(DEFAULT_CANTIDAD_A_SACAR)
            .observaciones(DEFAULT_OBSERVACIONES);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        salida.setCuentaSitio(cuentaSitio);
        // Add required entity
        MotivoSalida motivoSalida;
        if (TestUtil.findAll(em, MotivoSalida.class).isEmpty()) {
            motivoSalida = MotivoSalidaResourceIT.createEntity();
            em.persist(motivoSalida);
            em.flush();
        } else {
            motivoSalida = TestUtil.findAll(em, MotivoSalida.class).getFirst();
        }
        salida.setMotivoSalida(motivoSalida);
        // Add required entity
        EvidenciaSalida evidenciaSalida;
        if (TestUtil.findAll(em, EvidenciaSalida.class).isEmpty()) {
            evidenciaSalida = EvidenciaSalidaResourceIT.createEntity(em);
            em.persist(evidenciaSalida);
            em.flush();
        } else {
            evidenciaSalida = TestUtil.findAll(em, EvidenciaSalida.class).getFirst();
        }
        salida.setEvidenciaSalida(evidenciaSalida);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        salida.getProductoSitioses().add(productoSitio);
        return salida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salida createUpdatedEntity(EntityManager em) {
        Salida updatedSalida = new Salida()
            .respaldoImagen(UPDATED_RESPALDO_IMAGEN)
            .cantidadASacar(UPDATED_CANTIDAD_A_SACAR)
            .observaciones(UPDATED_OBSERVACIONES);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createUpdatedEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        updatedSalida.setCuentaSitio(cuentaSitio);
        // Add required entity
        MotivoSalida motivoSalida;
        if (TestUtil.findAll(em, MotivoSalida.class).isEmpty()) {
            motivoSalida = MotivoSalidaResourceIT.createUpdatedEntity();
            em.persist(motivoSalida);
            em.flush();
        } else {
            motivoSalida = TestUtil.findAll(em, MotivoSalida.class).getFirst();
        }
        updatedSalida.setMotivoSalida(motivoSalida);
        // Add required entity
        EvidenciaSalida evidenciaSalida;
        if (TestUtil.findAll(em, EvidenciaSalida.class).isEmpty()) {
            evidenciaSalida = EvidenciaSalidaResourceIT.createUpdatedEntity(em);
            em.persist(evidenciaSalida);
            em.flush();
        } else {
            evidenciaSalida = TestUtil.findAll(em, EvidenciaSalida.class).getFirst();
        }
        updatedSalida.setEvidenciaSalida(evidenciaSalida);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createUpdatedEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        updatedSalida.getProductoSitioses().add(productoSitio);
        return updatedSalida;
    }

    @BeforeEach
    void initTest() {
        salida = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSalida != null) {
            salidaRepository.delete(insertedSalida);
            insertedSalida = null;
        }
    }

    @Test
    @Transactional
    void createSalida() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);
        var returnedSalidaDTO = om.readValue(
            restSalidaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalidaDTO.class
        );

        // Validate the Salida in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSalida = salidaMapper.toEntity(returnedSalidaDTO);
        assertSalidaUpdatableFieldsEquals(returnedSalida, getPersistedSalida(returnedSalida));

        insertedSalida = returnedSalida;
    }

    @Test
    @Transactional
    void createSalidaWithExistingId() throws Exception {
        // Create the Salida with an existing ID
        salida.setId(1L);
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadASacarIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        salida.setCantidadASacar(null);

        // Create the Salida, which fails.
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        restSalidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSalidas() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        // Get all the salidaList
        restSalidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salida.getId().intValue())))
            .andExpect(jsonPath("$.[*].respaldoImagen").value(hasItem(DEFAULT_RESPALDO_IMAGEN)))
            .andExpect(jsonPath("$.[*].cantidadASacar").value(hasItem(DEFAULT_CANTIDAD_A_SACAR)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalidasWithEagerRelationshipsIsEnabled() throws Exception {
        when(salidaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalidaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(salidaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalidasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(salidaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalidaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(salidaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSalida() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        // Get the salida
        restSalidaMockMvc
            .perform(get(ENTITY_API_URL_ID, salida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salida.getId().intValue()))
            .andExpect(jsonPath("$.respaldoImagen").value(DEFAULT_RESPALDO_IMAGEN))
            .andExpect(jsonPath("$.cantidadASacar").value(DEFAULT_CANTIDAD_A_SACAR))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }

    @Test
    @Transactional
    void getNonExistingSalida() throws Exception {
        // Get the salida
        restSalidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalida() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salida
        Salida updatedSalida = salidaRepository.findById(salida.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalida are not directly saved in db
        em.detach(updatedSalida);
        updatedSalida.respaldoImagen(UPDATED_RESPALDO_IMAGEN).cantidadASacar(UPDATED_CANTIDAD_A_SACAR).observaciones(UPDATED_OBSERVACIONES);
        SalidaDTO salidaDTO = salidaMapper.toDto(updatedSalida);

        restSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salidaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalidaToMatchAllProperties(updatedSalida);
    }

    @Test
    @Transactional
    void putNonExistingSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salidaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salida using partial update
        Salida partialUpdatedSalida = new Salida();
        partialUpdatedSalida.setId(salida.getId());

        partialUpdatedSalida.respaldoImagen(UPDATED_RESPALDO_IMAGEN).cantidadASacar(UPDATED_CANTIDAD_A_SACAR);

        restSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalida))
            )
            .andExpect(status().isOk());

        // Validate the Salida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalidaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSalida, salida), getPersistedSalida(salida));
    }

    @Test
    @Transactional
    void fullUpdateSalidaWithPatch() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salida using partial update
        Salida partialUpdatedSalida = new Salida();
        partialUpdatedSalida.setId(salida.getId());

        partialUpdatedSalida
            .respaldoImagen(UPDATED_RESPALDO_IMAGEN)
            .cantidadASacar(UPDATED_CANTIDAD_A_SACAR)
            .observaciones(UPDATED_OBSERVACIONES);

        restSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalida))
            )
            .andExpect(status().isOk());

        // Validate the Salida in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalidaUpdatableFieldsEquals(partialUpdatedSalida, getPersistedSalida(partialUpdatedSalida));
    }

    @Test
    @Transactional
    void patchNonExistingSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salidaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalida() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salida.setId(longCount.incrementAndGet());

        // Create the Salida
        SalidaDTO salidaDTO = salidaMapper.toDto(salida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalidaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salidaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salida in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalida() throws Exception {
        // Initialize the database
        insertedSalida = salidaRepository.saveAndFlush(salida);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salida
        restSalidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, salida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salidaRepository.count();
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

    protected Salida getPersistedSalida(Salida salida) {
        return salidaRepository.findById(salida.getId()).orElseThrow();
    }

    protected void assertPersistedSalidaToMatchAllProperties(Salida expectedSalida) {
        assertSalidaAllPropertiesEquals(expectedSalida, getPersistedSalida(expectedSalida));
    }

    protected void assertPersistedSalidaToMatchUpdatableProperties(Salida expectedSalida) {
        assertSalidaAllUpdatablePropertiesEquals(expectedSalida, getPersistedSalida(expectedSalida));
    }
}
