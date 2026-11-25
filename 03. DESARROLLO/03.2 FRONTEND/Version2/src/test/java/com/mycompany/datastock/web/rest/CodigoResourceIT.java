package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.CodigoAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.enumeration.EstadoOperacion;
import com.mycompany.datastock.repository.CodigoRepository;
import com.mycompany.datastock.service.dto.CodigoDTO;
import com.mycompany.datastock.service.mapper.CodigoMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
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
 * Integration tests for the {@link CodigoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodigoResourceIT {

    private static final LocalDate DEFAULT_FECHA_REMISION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_REMISION = LocalDate.parse("2023-12-04");

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final EstadoOperacion DEFAULT_ESTADO_OPERACION = EstadoOperacion.PENDIENTE;
    private static final EstadoOperacion UPDATED_ESTADO_OPERACION = EstadoOperacion.ACEPTADA;

    private static final String ENTITY_API_URL = "/api/codigos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CodigoRepository codigoRepository;

    @Autowired
    private CodigoMapper codigoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodigoMockMvc;

    private Codigo codigo;

    private Codigo insertedCodigo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Codigo createEntity(EntityManager em) {
        Codigo codigo = new Codigo().fechaRemision(DEFAULT_FECHA_REMISION).codigo(DEFAULT_CODIGO).estadoOperacion(DEFAULT_ESTADO_OPERACION);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        codigo.setCuentaSitio(cuentaSitio);
        return codigo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Codigo createUpdatedEntity(EntityManager em) {
        Codigo updatedCodigo = new Codigo()
            .fechaRemision(UPDATED_FECHA_REMISION)
            .codigo(UPDATED_CODIGO)
            .estadoOperacion(UPDATED_ESTADO_OPERACION);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createUpdatedEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        updatedCodigo.setCuentaSitio(cuentaSitio);
        return updatedCodigo;
    }

    @BeforeEach
    void initTest() {
        codigo = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCodigo != null) {
            codigoRepository.delete(insertedCodigo);
            insertedCodigo = null;
        }
    }

    @Test
    @Transactional
    void createCodigo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);
        var returnedCodigoDTO = om.readValue(
            restCodigoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CodigoDTO.class
        );

        // Validate the Codigo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCodigo = codigoMapper.toEntity(returnedCodigoDTO);
        assertCodigoUpdatableFieldsEquals(returnedCodigo, getPersistedCodigo(returnedCodigo));

        insertedCodigo = returnedCodigo;
    }

    @Test
    @Transactional
    void createCodigoWithExistingId() throws Exception {
        // Create the Codigo with an existing ID
        codigo.setId(1L);
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodigoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaRemisionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codigo.setFechaRemision(null);

        // Create the Codigo, which fails.
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        restCodigoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codigo.setCodigo(null);

        // Create the Codigo, which fails.
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        restCodigoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoOperacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codigo.setEstadoOperacion(null);

        // Create the Codigo, which fails.
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        restCodigoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodigos() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        // Get all the codigoList
        restCodigoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaRemision").value(hasItem(DEFAULT_FECHA_REMISION.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].estadoOperacion").value(hasItem(DEFAULT_ESTADO_OPERACION.toString())));
    }

    @Test
    @Transactional
    void getCodigo() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        // Get the codigo
        restCodigoMockMvc
            .perform(get(ENTITY_API_URL_ID, codigo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codigo.getId().intValue()))
            .andExpect(jsonPath("$.fechaRemision").value(DEFAULT_FECHA_REMISION.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.estadoOperacion").value(DEFAULT_ESTADO_OPERACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCodigo() throws Exception {
        // Get the codigo
        restCodigoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodigo() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codigo
        Codigo updatedCodigo = codigoRepository.findById(codigo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCodigo are not directly saved in db
        em.detach(updatedCodigo);
        updatedCodigo.fechaRemision(UPDATED_FECHA_REMISION).codigo(UPDATED_CODIGO).estadoOperacion(UPDATED_ESTADO_OPERACION);
        CodigoDTO codigoDTO = codigoMapper.toDto(updatedCodigo);

        restCodigoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codigoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCodigoToMatchAllProperties(updatedCodigo);
    }

    @Test
    @Transactional
    void putNonExistingCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codigoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codigoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodigoWithPatch() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codigo using partial update
        Codigo partialUpdatedCodigo = new Codigo();
        partialUpdatedCodigo.setId(codigo.getId());

        partialUpdatedCodigo.fechaRemision(UPDATED_FECHA_REMISION).codigo(UPDATED_CODIGO);

        restCodigoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodigo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodigo))
            )
            .andExpect(status().isOk());

        // Validate the Codigo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodigoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCodigo, codigo), getPersistedCodigo(codigo));
    }

    @Test
    @Transactional
    void fullUpdateCodigoWithPatch() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codigo using partial update
        Codigo partialUpdatedCodigo = new Codigo();
        partialUpdatedCodigo.setId(codigo.getId());

        partialUpdatedCodigo.fechaRemision(UPDATED_FECHA_REMISION).codigo(UPDATED_CODIGO).estadoOperacion(UPDATED_ESTADO_OPERACION);

        restCodigoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodigo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodigo))
            )
            .andExpect(status().isOk());

        // Validate the Codigo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodigoUpdatableFieldsEquals(partialUpdatedCodigo, getPersistedCodigo(partialUpdatedCodigo));
    }

    @Test
    @Transactional
    void patchNonExistingCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codigoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codigoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codigoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodigo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codigo.setId(longCount.incrementAndGet());

        // Create the Codigo
        CodigoDTO codigoDTO = codigoMapper.toDto(codigo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodigoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(codigoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Codigo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodigo() throws Exception {
        // Initialize the database
        insertedCodigo = codigoRepository.saveAndFlush(codigo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the codigo
        restCodigoMockMvc
            .perform(delete(ENTITY_API_URL_ID, codigo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return codigoRepository.count();
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

    protected Codigo getPersistedCodigo(Codigo codigo) {
        return codigoRepository.findById(codigo.getId()).orElseThrow();
    }

    protected void assertPersistedCodigoToMatchAllProperties(Codigo expectedCodigo) {
        assertCodigoAllPropertiesEquals(expectedCodigo, getPersistedCodigo(expectedCodigo));
    }

    protected void assertPersistedCodigoToMatchUpdatableProperties(Codigo expectedCodigo) {
        assertCodigoAllUpdatablePropertiesEquals(expectedCodigo, getPersistedCodigo(expectedCodigo));
    }
}
