package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.TrasladoAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.domain.Traslado;
import com.mycompany.datastock.repository.TrasladoRepository;
import com.mycompany.datastock.service.TrasladoService;
import com.mycompany.datastock.service.dto.TrasladoDTO;
import com.mycompany.datastock.service.mapper.TrasladoMapper;
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
 * Integration tests for the {@link TrasladoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TrasladoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/traslados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrasladoRepository trasladoRepository;

    @Mock
    private TrasladoRepository trasladoRepositoryMock;

    @Autowired
    private TrasladoMapper trasladoMapper;

    @Mock
    private TrasladoService trasladoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrasladoMockMvc;

    private Traslado traslado;

    private Traslado insertedTraslado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traslado createEntity(EntityManager em) {
        Traslado traslado = new Traslado().descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        traslado.setCuentaSitio(cuentaSitio);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        traslado.setSitioDestino(sitio);
        // Add required entity
        Codigo codigo;
        if (TestUtil.findAll(em, Codigo.class).isEmpty()) {
            codigo = CodigoResourceIT.createEntity(em);
            em.persist(codigo);
            em.flush();
        } else {
            codigo = TestUtil.findAll(em, Codigo.class).getFirst();
        }
        traslado.setCodigo(codigo);
        return traslado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traslado createUpdatedEntity(EntityManager em) {
        Traslado updatedTraslado = new Traslado().descripcion(UPDATED_DESCRIPCION);
        // Add required entity
        CuentaSitio cuentaSitio;
        if (TestUtil.findAll(em, CuentaSitio.class).isEmpty()) {
            cuentaSitio = CuentaSitioResourceIT.createUpdatedEntity(em);
            em.persist(cuentaSitio);
            em.flush();
        } else {
            cuentaSitio = TestUtil.findAll(em, CuentaSitio.class).getFirst();
        }
        updatedTraslado.setCuentaSitio(cuentaSitio);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createUpdatedEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        updatedTraslado.setSitioDestino(sitio);
        // Add required entity
        Codigo codigo;
        if (TestUtil.findAll(em, Codigo.class).isEmpty()) {
            codigo = CodigoResourceIT.createUpdatedEntity(em);
            em.persist(codigo);
            em.flush();
        } else {
            codigo = TestUtil.findAll(em, Codigo.class).getFirst();
        }
        updatedTraslado.setCodigo(codigo);
        return updatedTraslado;
    }

    @BeforeEach
    void initTest() {
        traslado = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTraslado != null) {
            trasladoRepository.delete(insertedTraslado);
            insertedTraslado = null;
        }
    }

    @Test
    @Transactional
    void createTraslado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);
        var returnedTrasladoDTO = om.readValue(
            restTrasladoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrasladoDTO.class
        );

        // Validate the Traslado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTraslado = trasladoMapper.toEntity(returnedTrasladoDTO);
        assertTrasladoUpdatableFieldsEquals(returnedTraslado, getPersistedTraslado(returnedTraslado));

        insertedTraslado = returnedTraslado;
    }

    @Test
    @Transactional
    void createTrasladoWithExistingId() throws Exception {
        // Create the Traslado with an existing ID
        traslado.setId(1L);
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrasladoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTraslados() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        // Get all the trasladoList
        restTrasladoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traslado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrasladosWithEagerRelationshipsIsEnabled() throws Exception {
        when(trasladoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrasladoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(trasladoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrasladosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(trasladoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrasladoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(trasladoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTraslado() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        // Get the traslado
        restTrasladoMockMvc
            .perform(get(ENTITY_API_URL_ID, traslado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traslado.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTraslado() throws Exception {
        // Get the traslado
        restTrasladoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTraslado() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traslado
        Traslado updatedTraslado = trasladoRepository.findById(traslado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTraslado are not directly saved in db
        em.detach(updatedTraslado);
        updatedTraslado.descripcion(UPDATED_DESCRIPCION);
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(updatedTraslado);

        restTrasladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trasladoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrasladoToMatchAllProperties(updatedTraslado);
    }

    @Test
    @Transactional
    void putNonExistingTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trasladoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trasladoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trasladoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrasladoWithPatch() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traslado using partial update
        Traslado partialUpdatedTraslado = new Traslado();
        partialUpdatedTraslado.setId(traslado.getId());

        partialUpdatedTraslado.descripcion(UPDATED_DESCRIPCION);

        restTrasladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraslado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTraslado))
            )
            .andExpect(status().isOk());

        // Validate the Traslado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrasladoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTraslado, traslado), getPersistedTraslado(traslado));
    }

    @Test
    @Transactional
    void fullUpdateTrasladoWithPatch() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traslado using partial update
        Traslado partialUpdatedTraslado = new Traslado();
        partialUpdatedTraslado.setId(traslado.getId());

        partialUpdatedTraslado.descripcion(UPDATED_DESCRIPCION);

        restTrasladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraslado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTraslado))
            )
            .andExpect(status().isOk());

        // Validate the Traslado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrasladoUpdatableFieldsEquals(partialUpdatedTraslado, getPersistedTraslado(partialUpdatedTraslado));
    }

    @Test
    @Transactional
    void patchNonExistingTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trasladoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trasladoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trasladoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraslado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traslado.setId(longCount.incrementAndGet());

        // Create the Traslado
        TrasladoDTO trasladoDTO = trasladoMapper.toDto(traslado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrasladoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trasladoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traslado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraslado() throws Exception {
        // Initialize the database
        insertedTraslado = trasladoRepository.saveAndFlush(traslado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the traslado
        restTrasladoMockMvc
            .perform(delete(ENTITY_API_URL_ID, traslado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trasladoRepository.count();
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

    protected Traslado getPersistedTraslado(Traslado traslado) {
        return trasladoRepository.findById(traslado.getId()).orElseThrow();
    }

    protected void assertPersistedTrasladoToMatchAllProperties(Traslado expectedTraslado) {
        assertTrasladoAllPropertiesEquals(expectedTraslado, getPersistedTraslado(expectedTraslado));
    }

    protected void assertPersistedTrasladoToMatchUpdatableProperties(Traslado expectedTraslado) {
        assertTrasladoAllUpdatablePropertiesEquals(expectedTraslado, getPersistedTraslado(expectedTraslado));
    }
}
