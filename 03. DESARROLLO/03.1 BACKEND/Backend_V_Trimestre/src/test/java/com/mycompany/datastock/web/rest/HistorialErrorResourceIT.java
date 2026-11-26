package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.HistorialErrorAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.HistorialError;
import com.mycompany.datastock.repository.HistorialErrorRepository;
import com.mycompany.datastock.service.HistorialErrorService;
import com.mycompany.datastock.service.dto.HistorialErrorDTO;
import com.mycompany.datastock.service.mapper.HistorialErrorMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link HistorialErrorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HistorialErrorResourceIT {

    private static final String DEFAULT_NOMBRE_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ERROR = "BBBBBBBBBB";

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA = Instant.ofEpochMilli(1701702194643L);

    private static final String ENTITY_API_URL = "/api/historial-errors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HistorialErrorRepository historialErrorRepository;

    @Mock
    private HistorialErrorRepository historialErrorRepositoryMock;

    @Autowired
    private HistorialErrorMapper historialErrorMapper;

    @Mock
    private HistorialErrorService historialErrorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistorialErrorMockMvc;

    private HistorialError historialError;

    private HistorialError insertedHistorialError;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialError createEntity(EntityManager em) {
        HistorialError historialError = new HistorialError()
            .nombreError(DEFAULT_NOMBRE_ERROR)
            .mensaje(DEFAULT_MENSAJE)
            .fechaHora(DEFAULT_FECHA_HORA);
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        historialError.setCuenta(cuenta);
        return historialError;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialError createUpdatedEntity(EntityManager em) {
        HistorialError updatedHistorialError = new HistorialError()
            .nombreError(UPDATED_NOMBRE_ERROR)
            .mensaje(UPDATED_MENSAJE)
            .fechaHora(UPDATED_FECHA_HORA);
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createUpdatedEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        updatedHistorialError.setCuenta(cuenta);
        return updatedHistorialError;
    }

    @BeforeEach
    void initTest() {
        historialError = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedHistorialError != null) {
            historialErrorRepository.delete(insertedHistorialError);
            insertedHistorialError = null;
        }
    }

    @Test
    @Transactional
    void createHistorialError() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);
        var returnedHistorialErrorDTO = om.readValue(
            restHistorialErrorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HistorialErrorDTO.class
        );

        // Validate the HistorialError in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHistorialError = historialErrorMapper.toEntity(returnedHistorialErrorDTO);
        assertHistorialErrorUpdatableFieldsEquals(returnedHistorialError, getPersistedHistorialError(returnedHistorialError));

        insertedHistorialError = returnedHistorialError;
    }

    @Test
    @Transactional
    void createHistorialErrorWithExistingId() throws Exception {
        // Create the HistorialError with an existing ID
        historialError.setId(1L);
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorialErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreErrorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        historialError.setNombreError(null);

        // Create the HistorialError, which fails.
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        restHistorialErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMensajeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        historialError.setMensaje(null);

        // Create the HistorialError, which fails.
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        restHistorialErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaHoraIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        historialError.setFechaHora(null);

        // Create the HistorialError, which fails.
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        restHistorialErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistorialErrors() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        // Get all the historialErrorList
        restHistorialErrorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialError.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreError").value(hasItem(DEFAULT_NOMBRE_ERROR)))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(DEFAULT_FECHA_HORA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHistorialErrorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(historialErrorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistorialErrorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(historialErrorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHistorialErrorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(historialErrorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistorialErrorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(historialErrorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getHistorialError() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        // Get the historialError
        restHistorialErrorMockMvc
            .perform(get(ENTITY_API_URL_ID, historialError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historialError.getId().intValue()))
            .andExpect(jsonPath("$.nombreError").value(DEFAULT_NOMBRE_ERROR))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.fechaHora").value(DEFAULT_FECHA_HORA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHistorialError() throws Exception {
        // Get the historialError
        restHistorialErrorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistorialError() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historialError
        HistorialError updatedHistorialError = historialErrorRepository.findById(historialError.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHistorialError are not directly saved in db
        em.detach(updatedHistorialError);
        updatedHistorialError.nombreError(UPDATED_NOMBRE_ERROR).mensaje(UPDATED_MENSAJE).fechaHora(UPDATED_FECHA_HORA);
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(updatedHistorialError);

        restHistorialErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historialErrorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historialErrorDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHistorialErrorToMatchAllProperties(updatedHistorialError);
    }

    @Test
    @Transactional
    void putNonExistingHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historialErrorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historialErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historialErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistorialErrorWithPatch() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historialError using partial update
        HistorialError partialUpdatedHistorialError = new HistorialError();
        partialUpdatedHistorialError.setId(historialError.getId());

        partialUpdatedHistorialError.nombreError(UPDATED_NOMBRE_ERROR).mensaje(UPDATED_MENSAJE).fechaHora(UPDATED_FECHA_HORA);

        restHistorialErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorialError.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorialError))
            )
            .andExpect(status().isOk());

        // Validate the HistorialError in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorialErrorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHistorialError, historialError),
            getPersistedHistorialError(historialError)
        );
    }

    @Test
    @Transactional
    void fullUpdateHistorialErrorWithPatch() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historialError using partial update
        HistorialError partialUpdatedHistorialError = new HistorialError();
        partialUpdatedHistorialError.setId(historialError.getId());

        partialUpdatedHistorialError.nombreError(UPDATED_NOMBRE_ERROR).mensaje(UPDATED_MENSAJE).fechaHora(UPDATED_FECHA_HORA);

        restHistorialErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorialError.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorialError))
            )
            .andExpect(status().isOk());

        // Validate the HistorialError in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorialErrorUpdatableFieldsEquals(partialUpdatedHistorialError, getPersistedHistorialError(partialUpdatedHistorialError));
    }

    @Test
    @Transactional
    void patchNonExistingHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historialErrorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historialErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historialErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistorialError() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historialError.setId(longCount.incrementAndGet());

        // Create the HistorialError
        HistorialErrorDTO historialErrorDTO = historialErrorMapper.toDto(historialError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorialErrorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(historialErrorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistorialError in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistorialError() throws Exception {
        // Initialize the database
        insertedHistorialError = historialErrorRepository.saveAndFlush(historialError);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the historialError
        restHistorialErrorMockMvc
            .perform(delete(ENTITY_API_URL_ID, historialError.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return historialErrorRepository.count();
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

    protected HistorialError getPersistedHistorialError(HistorialError historialError) {
        return historialErrorRepository.findById(historialError.getId()).orElseThrow();
    }

    protected void assertPersistedHistorialErrorToMatchAllProperties(HistorialError expectedHistorialError) {
        assertHistorialErrorAllPropertiesEquals(expectedHistorialError, getPersistedHistorialError(expectedHistorialError));
    }

    protected void assertPersistedHistorialErrorToMatchUpdatableProperties(HistorialError expectedHistorialError) {
        assertHistorialErrorAllUpdatablePropertiesEquals(expectedHistorialError, getPersistedHistorialError(expectedHistorialError));
    }
}
