package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.RemisionAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Remision;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.RemisionRepository;
import com.mycompany.datastock.service.RemisionService;
import com.mycompany.datastock.service.dto.RemisionDTO;
import com.mycompany.datastock.service.mapper.RemisionMapper;
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
 * Integration tests for the {@link RemisionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RemisionResourceIT {

    private static final Integer DEFAULT_CANTIDAD_REMITIDA = 0;
    private static final Integer UPDATED_CANTIDAD_REMITIDA = 1;

    private static final String ENTITY_API_URL = "/api/remisions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RemisionRepository remisionRepository;

    @Mock
    private RemisionRepository remisionRepositoryMock;

    @Autowired
    private RemisionMapper remisionMapper;

    @Mock
    private RemisionService remisionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemisionMockMvc;

    private Remision remision;

    private Remision insertedRemision;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remision createEntity(EntityManager em) {
        Remision remision = new Remision().cantidadRemitida(DEFAULT_CANTIDAD_REMITIDA);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        remision.setProductoSitio(productoSitio);
        // Add required entity
        Codigo codigo;
        if (TestUtil.findAll(em, Codigo.class).isEmpty()) {
            codigo = CodigoResourceIT.createEntity(em);
            em.persist(codigo);
            em.flush();
        } else {
            codigo = TestUtil.findAll(em, Codigo.class).getFirst();
        }
        remision.setCodigo(codigo);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        remision.setSitioDestino(sitio);
        return remision;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remision createUpdatedEntity(EntityManager em) {
        Remision updatedRemision = new Remision().cantidadRemitida(UPDATED_CANTIDAD_REMITIDA);
        // Add required entity
        ProductoSitio productoSitio;
        if (TestUtil.findAll(em, ProductoSitio.class).isEmpty()) {
            productoSitio = ProductoSitioResourceIT.createUpdatedEntity(em);
            em.persist(productoSitio);
            em.flush();
        } else {
            productoSitio = TestUtil.findAll(em, ProductoSitio.class).getFirst();
        }
        updatedRemision.setProductoSitio(productoSitio);
        // Add required entity
        Codigo codigo;
        if (TestUtil.findAll(em, Codigo.class).isEmpty()) {
            codigo = CodigoResourceIT.createUpdatedEntity(em);
            em.persist(codigo);
            em.flush();
        } else {
            codigo = TestUtil.findAll(em, Codigo.class).getFirst();
        }
        updatedRemision.setCodigo(codigo);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createUpdatedEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        updatedRemision.setSitioDestino(sitio);
        return updatedRemision;
    }

    @BeforeEach
    void initTest() {
        remision = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedRemision != null) {
            remisionRepository.delete(insertedRemision);
            insertedRemision = null;
        }
    }

    @Test
    @Transactional
    void createRemision() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);
        var returnedRemisionDTO = om.readValue(
            restRemisionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remisionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RemisionDTO.class
        );

        // Validate the Remision in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRemision = remisionMapper.toEntity(returnedRemisionDTO);
        assertRemisionUpdatableFieldsEquals(returnedRemision, getPersistedRemision(returnedRemision));

        insertedRemision = returnedRemision;
    }

    @Test
    @Transactional
    void createRemisionWithExistingId() throws Exception {
        // Create the Remision with an existing ID
        remision.setId(1L);
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemisionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadRemitidaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        remision.setCantidadRemitida(null);

        // Create the Remision, which fails.
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        restRemisionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remisionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRemisions() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        // Get all the remisionList
        restRemisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remision.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadRemitida").value(hasItem(DEFAULT_CANTIDAD_REMITIDA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRemisionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(remisionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRemisionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(remisionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRemisionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(remisionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRemisionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(remisionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRemision() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        // Get the remision
        restRemisionMockMvc
            .perform(get(ENTITY_API_URL_ID, remision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remision.getId().intValue()))
            .andExpect(jsonPath("$.cantidadRemitida").value(DEFAULT_CANTIDAD_REMITIDA));
    }

    @Test
    @Transactional
    void getNonExistingRemision() throws Exception {
        // Get the remision
        restRemisionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemision() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remision
        Remision updatedRemision = remisionRepository.findById(remision.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRemision are not directly saved in db
        em.detach(updatedRemision);
        updatedRemision.cantidadRemitida(UPDATED_CANTIDAD_REMITIDA);
        RemisionDTO remisionDTO = remisionMapper.toDto(updatedRemision);

        restRemisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remisionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remisionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRemisionToMatchAllProperties(updatedRemision);
    }

    @Test
    @Transactional
    void putNonExistingRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remisionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remisionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemisionWithPatch() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remision using partial update
        Remision partialUpdatedRemision = new Remision();
        partialUpdatedRemision.setId(remision.getId());

        partialUpdatedRemision.cantidadRemitida(UPDATED_CANTIDAD_REMITIDA);

        restRemisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemision.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemision))
            )
            .andExpect(status().isOk());

        // Validate the Remision in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemisionUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRemision, remision), getPersistedRemision(remision));
    }

    @Test
    @Transactional
    void fullUpdateRemisionWithPatch() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remision using partial update
        Remision partialUpdatedRemision = new Remision();
        partialUpdatedRemision.setId(remision.getId());

        partialUpdatedRemision.cantidadRemitida(UPDATED_CANTIDAD_REMITIDA);

        restRemisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemision.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemision))
            )
            .andExpect(status().isOk());

        // Validate the Remision in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemisionUpdatableFieldsEquals(partialUpdatedRemision, getPersistedRemision(partialUpdatedRemision));
    }

    @Test
    @Transactional
    void patchNonExistingRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remisionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remision.setId(longCount.incrementAndGet());

        // Create the Remision
        RemisionDTO remisionDTO = remisionMapper.toDto(remision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemisionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(remisionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemision() throws Exception {
        // Initialize the database
        insertedRemision = remisionRepository.saveAndFlush(remision);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the remision
        restRemisionMockMvc
            .perform(delete(ENTITY_API_URL_ID, remision.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return remisionRepository.count();
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

    protected Remision getPersistedRemision(Remision remision) {
        return remisionRepository.findById(remision.getId()).orElseThrow();
    }

    protected void assertPersistedRemisionToMatchAllProperties(Remision expectedRemision) {
        assertRemisionAllPropertiesEquals(expectedRemision, getPersistedRemision(expectedRemision));
    }

    protected void assertPersistedRemisionToMatchUpdatableProperties(Remision expectedRemision) {
        assertRemisionAllUpdatablePropertiesEquals(expectedRemision, getPersistedRemision(expectedRemision));
    }
}
