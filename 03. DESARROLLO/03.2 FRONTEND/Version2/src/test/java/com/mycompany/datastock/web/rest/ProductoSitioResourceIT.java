package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.ProductoSitioAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.EstadoProducto;
import com.mycompany.datastock.domain.Lote;
import com.mycompany.datastock.domain.Producto;
import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.ProductoSitioRepository;
import com.mycompany.datastock.service.ProductoSitioService;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.mapper.ProductoSitioMapper;
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
 * Integration tests for the {@link ProductoSitioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductoSitioResourceIT {

    private static final Integer DEFAULT_CANTIDAD_SITIO = 0;
    private static final Integer UPDATED_CANTIDAD_SITIO = 1;

    private static final String ENTITY_API_URL = "/api/producto-sitios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductoSitioRepository productoSitioRepository;

    @Mock
    private ProductoSitioRepository productoSitioRepositoryMock;

    @Autowired
    private ProductoSitioMapper productoSitioMapper;

    @Mock
    private ProductoSitioService productoSitioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoSitioMockMvc;

    private ProductoSitio productoSitio;

    private ProductoSitio insertedProductoSitio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoSitio createEntity(EntityManager em) {
        ProductoSitio productoSitio = new ProductoSitio().cantidadSitio(DEFAULT_CANTIDAD_SITIO);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).getFirst();
        }
        productoSitio.setProducto(producto);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        productoSitio.setSitio(sitio);
        // Add required entity
        Lote lote;
        if (TestUtil.findAll(em, Lote.class).isEmpty()) {
            lote = LoteResourceIT.createEntity(em);
            em.persist(lote);
            em.flush();
        } else {
            lote = TestUtil.findAll(em, Lote.class).getFirst();
        }
        productoSitio.setLote(lote);
        // Add required entity
        EstadoProducto estadoProducto;
        if (TestUtil.findAll(em, EstadoProducto.class).isEmpty()) {
            estadoProducto = EstadoProductoResourceIT.createEntity();
            em.persist(estadoProducto);
            em.flush();
        } else {
            estadoProducto = TestUtil.findAll(em, EstadoProducto.class).getFirst();
        }
        productoSitio.setEstadoProducto(estadoProducto);
        return productoSitio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoSitio createUpdatedEntity(EntityManager em) {
        ProductoSitio updatedProductoSitio = new ProductoSitio().cantidadSitio(UPDATED_CANTIDAD_SITIO);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).getFirst();
        }
        updatedProductoSitio.setProducto(producto);
        // Add required entity
        Sitio sitio;
        if (TestUtil.findAll(em, Sitio.class).isEmpty()) {
            sitio = SitioResourceIT.createUpdatedEntity();
            em.persist(sitio);
            em.flush();
        } else {
            sitio = TestUtil.findAll(em, Sitio.class).getFirst();
        }
        updatedProductoSitio.setSitio(sitio);
        // Add required entity
        Lote lote;
        if (TestUtil.findAll(em, Lote.class).isEmpty()) {
            lote = LoteResourceIT.createUpdatedEntity(em);
            em.persist(lote);
            em.flush();
        } else {
            lote = TestUtil.findAll(em, Lote.class).getFirst();
        }
        updatedProductoSitio.setLote(lote);
        // Add required entity
        EstadoProducto estadoProducto;
        if (TestUtil.findAll(em, EstadoProducto.class).isEmpty()) {
            estadoProducto = EstadoProductoResourceIT.createUpdatedEntity();
            em.persist(estadoProducto);
            em.flush();
        } else {
            estadoProducto = TestUtil.findAll(em, EstadoProducto.class).getFirst();
        }
        updatedProductoSitio.setEstadoProducto(estadoProducto);
        return updatedProductoSitio;
    }

    @BeforeEach
    void initTest() {
        productoSitio = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedProductoSitio != null) {
            productoSitioRepository.delete(insertedProductoSitio);
            insertedProductoSitio = null;
        }
    }

    @Test
    @Transactional
    void createProductoSitio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);
        var returnedProductoSitioDTO = om.readValue(
            restProductoSitioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productoSitioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProductoSitioDTO.class
        );

        // Validate the ProductoSitio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProductoSitio = productoSitioMapper.toEntity(returnedProductoSitioDTO);
        assertProductoSitioUpdatableFieldsEquals(returnedProductoSitio, getPersistedProductoSitio(returnedProductoSitio));

        insertedProductoSitio = returnedProductoSitio;
    }

    @Test
    @Transactional
    void createProductoSitioWithExistingId() throws Exception {
        // Create the ProductoSitio with an existing ID
        productoSitio.setId(1L);
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productoSitioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadSitioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        productoSitio.setCantidadSitio(null);

        // Create the ProductoSitio, which fails.
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        restProductoSitioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productoSitioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductoSitios() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        // Get all the productoSitioList
        restProductoSitioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoSitio.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadSitio").value(hasItem(DEFAULT_CANTIDAD_SITIO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductoSitiosWithEagerRelationshipsIsEnabled() throws Exception {
        when(productoSitioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoSitioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productoSitioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductoSitiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productoSitioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoSitioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(productoSitioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProductoSitio() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        // Get the productoSitio
        restProductoSitioMockMvc
            .perform(get(ENTITY_API_URL_ID, productoSitio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoSitio.getId().intValue()))
            .andExpect(jsonPath("$.cantidadSitio").value(DEFAULT_CANTIDAD_SITIO));
    }

    @Test
    @Transactional
    void getNonExistingProductoSitio() throws Exception {
        // Get the productoSitio
        restProductoSitioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductoSitio() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productoSitio
        ProductoSitio updatedProductoSitio = productoSitioRepository.findById(productoSitio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductoSitio are not directly saved in db
        em.detach(updatedProductoSitio);
        updatedProductoSitio.cantidadSitio(UPDATED_CANTIDAD_SITIO);
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(updatedProductoSitio);

        restProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productoSitioDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductoSitioToMatchAllProperties(updatedProductoSitio);
    }

    @Test
    @Transactional
    void putNonExistingProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoSitioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productoSitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoSitioWithPatch() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productoSitio using partial update
        ProductoSitio partialUpdatedProductoSitio = new ProductoSitio();
        partialUpdatedProductoSitio.setId(productoSitio.getId());

        restProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductoSitio))
            )
            .andExpect(status().isOk());

        // Validate the ProductoSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductoSitioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductoSitio, productoSitio),
            getPersistedProductoSitio(productoSitio)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductoSitioWithPatch() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productoSitio using partial update
        ProductoSitio partialUpdatedProductoSitio = new ProductoSitio();
        partialUpdatedProductoSitio.setId(productoSitio.getId());

        partialUpdatedProductoSitio.cantidadSitio(UPDATED_CANTIDAD_SITIO);

        restProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoSitio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductoSitio))
            )
            .andExpect(status().isOk());

        // Validate the ProductoSitio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductoSitioUpdatableFieldsEquals(partialUpdatedProductoSitio, getPersistedProductoSitio(partialUpdatedProductoSitio));
    }

    @Test
    @Transactional
    void patchNonExistingProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoSitioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productoSitioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoSitio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productoSitio.setId(longCount.incrementAndGet());

        // Create the ProductoSitio
        ProductoSitioDTO productoSitioDTO = productoSitioMapper.toDto(productoSitio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoSitioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productoSitioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoSitio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoSitio() throws Exception {
        // Initialize the database
        insertedProductoSitio = productoSitioRepository.saveAndFlush(productoSitio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productoSitio
        restProductoSitioMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoSitio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productoSitioRepository.count();
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

    protected ProductoSitio getPersistedProductoSitio(ProductoSitio productoSitio) {
        return productoSitioRepository.findById(productoSitio.getId()).orElseThrow();
    }

    protected void assertPersistedProductoSitioToMatchAllProperties(ProductoSitio expectedProductoSitio) {
        assertProductoSitioAllPropertiesEquals(expectedProductoSitio, getPersistedProductoSitio(expectedProductoSitio));
    }

    protected void assertPersistedProductoSitioToMatchUpdatableProperties(ProductoSitio expectedProductoSitio) {
        assertProductoSitioAllUpdatablePropertiesEquals(expectedProductoSitio, getPersistedProductoSitio(expectedProductoSitio));
    }
}
