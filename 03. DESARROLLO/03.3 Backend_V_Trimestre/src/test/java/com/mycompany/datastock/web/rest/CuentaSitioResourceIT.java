package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.CuentaSitioAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.CuentaSitioRepository;
import com.mycompany.datastock.service.CuentaSitioService;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import com.mycompany.datastock.service.mapper.CuentaSitioMapper;
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
 * Integration tests for the {@link CuentaSitioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CuentaSitioResourceIT {

    private static final String ENTITY_API_URL = "/api/cuenta-sitios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CuentaSitioRepository cuentaSitioRepository;

    @Mock
    private CuentaSitioRepository cuentaSitioRepositoryMock;

    @Autowired
    private CuentaSitioMapper cuentaSitioMapper;

    @Mock
    private CuentaSitioService cuentaSitioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuentaSitioMockMvc;

    private CuentaSitio cuentaSitio;

    private CuentaSitio insertedCuentaSitio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CuentaSitio createEntity(EntityManager em) {
        CuentaSitio cuentaSitio = new CuentaSitio();
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        cuentaSitio.setCuenta(cuenta);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        cuentaSitio.setSitio(sitio);
        return cuentaSitio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CuentaSitio createUpdatedEntity(EntityManager em) {
        CuentaSitio updatedCuentaSitio = new CuentaSitio();
        // Add required entity
        Cuenta cuenta;
        if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
            cuenta = CuentaResourceIT.createUpdatedEntity(em);
            em.persist(cuenta);
            em.flush();
        } else {
            cuenta = TestUtil.findAll(em, Cuenta.class).getFirst();
        }
        updatedCuentaSitio.setCuenta(cuenta);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createUpdatedEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        updatedCuentaSitio.setSitio(sitio);
        return updatedCuentaSitio;
    }

    @BeforeEach
    void initTest() {
        cuentaSitio = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCuentaSitio != null) {
            cuentaSitioRepository.delete(insertedCuentaSitio);
            insertedCuentaSitio = null;
        }
    }

    @Test
    @Transactional
    void createCuentaSitio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);
        var returnedCuentaSitioDTO = om.readValue(
            restCuentaSitioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaSitioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CuentaSitioDTO.class
        );

        // Validate the CuentaSitio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCuentaSitio = cuentaSitioMapper.toEntity(returnedCuentaSitioDTO);
        assertCuentaSitioUpdatableFieldsEquals(returnedCuentaSitio, getPersistedCuentaSitio(returnedCuentaSitio));

        insertedCuentaSitio = returnedCuentaSitio;
    }

    @Test
    @Transactional
    void createCuentaSitioWithExistingId() throws Exception {
        // Create the CuentaSitio with an existing ID
        cuentaSitio.setId(1L);
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaSitioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCuentaSitios() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        // Get all the cuentaSitioList
        restCuentaSitioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaSitio.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCuentaSitiosWithEagerRelationshipsIsEnabled() throws Exception {
        when(cuentaSitioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCuentaSitioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cuentaSitioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCuentaSitiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cuentaSitioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCuentaSitioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cuentaSitioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCuentaSitio() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        // Get the cuentaSitio
        restCuentaSitioMockMvc
            .perform(get(ENTITY_API_URL_ID, cuentaSitio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuentaSitio.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCuentaSitio() throws Exception {
        // Get the cuentaSitio
        restCuentaSitioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCuentaSitio() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentaSitio
        CuentaSitio updatedCuentaSitio = cuentaSitioRepository.findById(cuentaSitio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCuentaSitio are not directly saved in db
        em.detach(updatedCuentaSitio);
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(updatedCuentaSitio);

        restCuentaSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentaSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuentaSitioDTO))
            )
            .andExpect(status().isOk());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCuentaSitioToMatchAllProperties(updatedCuentaSitio);
    }

    @Test
    @Transactional
    void putNonExistingCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentaSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuentaSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuentaSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaSitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuentaSitioWithPatch() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentaSitio using partial update
        CuentaSitio partialUpdatedCuentaSitio = new CuentaSitio();
        partialUpdatedCuentaSitio.setId(cuentaSitio.getId());

        restCuentaSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuentaSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuentaSitio))
            )
            .andExpect(status().isOk());

        // Validate the CuentaSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentaSitioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCuentaSitio, cuentaSitio),
            getPersistedCuentaSitio(cuentaSitio)
        );
    }

    @Test
    @Transactional
    void fullUpdateCuentaSitioWithPatch() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentaSitio using partial update
        CuentaSitio partialUpdatedCuentaSitio = new CuentaSitio();
        partialUpdatedCuentaSitio.setId(cuentaSitio.getId());

        restCuentaSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuentaSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuentaSitio))
            )
            .andExpect(status().isOk());

        // Validate the CuentaSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentaSitioUpdatableFieldsEquals(partialUpdatedCuentaSitio, getPersistedCuentaSitio(partialUpdatedCuentaSitio));
    }

    @Test
    @Transactional
    void patchNonExistingCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuentaSitioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentaSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentaSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuentaSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentaSitio.setId(longCount.incrementAndGet());

        // Create the CuentaSitio
        CuentaSitioDTO cuentaSitioDTO = cuentaSitioMapper.toDto(cuentaSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaSitioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cuentaSitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CuentaSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuentaSitio() throws Exception {
        // Initialize the database
        insertedCuentaSitio = cuentaSitioRepository.saveAndFlush(cuentaSitio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cuentaSitio
        restCuentaSitioMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuentaSitio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cuentaSitioRepository.count();
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

    protected CuentaSitio getPersistedCuentaSitio(CuentaSitio cuentaSitio) {
        return cuentaSitioRepository.findById(cuentaSitio.getId()).orElseThrow();
    }

    protected void assertPersistedCuentaSitioToMatchAllProperties(CuentaSitio expectedCuentaSitio) {
        assertCuentaSitioAllPropertiesEquals(expectedCuentaSitio, getPersistedCuentaSitio(expectedCuentaSitio));
    }

    protected void assertPersistedCuentaSitioToMatchUpdatableProperties(CuentaSitio expectedCuentaSitio) {
        assertCuentaSitioAllUpdatablePropertiesEquals(expectedCuentaSitio, getPersistedCuentaSitio(expectedCuentaSitio));
    }
}
