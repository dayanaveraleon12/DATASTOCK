package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.LoteAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Lote;
import com.mycompany.datastock.domain.Producto;
import com.mycompany.datastock.repository.LoteRepository;
import com.mycompany.datastock.service.LoteService;
import com.mycompany.datastock.service.dto.LoteDTO;
import com.mycompany.datastock.service.mapper.LoteMapper;
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
 * Integration tests for the {@link LoteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LoteResourceIT {

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.parse("2023-12-04");

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.parse("2023-12-04");

    private static final String DEFAULT_CODIGO_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_FACTURA_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_FACTURA_PROVEEDOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_INGRESADA = 0;
    private static final Integer UPDATED_CANTIDAD_INGRESADA = 1;

    private static final String ENTITY_API_URL = "/api/lotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LoteRepository loteRepository;

    @Mock
    private LoteRepository loteRepositoryMock;

    @Autowired
    private LoteMapper loteMapper;

    @Mock
    private LoteService loteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoteMockMvc;

    private Lote lote;

    private Lote insertedLote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createEntity(EntityManager em) {
        Lote lote = new Lote()
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .codigoLote(DEFAULT_CODIGO_LOTE)
            .descripcion(DEFAULT_DESCRIPCION)
            .facturaProveedor(DEFAULT_FACTURA_PROVEEDOR)
            .cantidadIngresada(DEFAULT_CANTIDAD_INGRESADA);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).getFirst();
        }
        lote.setProducto(producto);
        return lote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createUpdatedEntity(EntityManager em) {
        Lote updatedLote = new Lote()
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .codigoLote(UPDATED_CODIGO_LOTE)
            .descripcion(UPDATED_DESCRIPCION)
            .facturaProveedor(UPDATED_FACTURA_PROVEEDOR)
            .cantidadIngresada(UPDATED_CANTIDAD_INGRESADA);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).getFirst();
        }
        updatedLote.setProducto(producto);
        return updatedLote;
    }

    @BeforeEach
    void initTest() {
        lote = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedLote != null) {
            loteRepository.delete(insertedLote);
            insertedLote = null;
        }
    }

    @Test
    @Transactional
    void createLote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);
        var returnedLoteDTO = om.readValue(
            restLoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LoteDTO.class
        );

        // Validate the Lote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLote = loteMapper.toEntity(returnedLoteDTO);
        assertLoteUpdatableFieldsEquals(returnedLote, getPersistedLote(returnedLote));

        insertedLote = returnedLote;
    }

    @Test
    @Transactional
    void createLoteWithExistingId() throws Exception {
        // Create the Lote with an existing ID
        lote.setId(1L);
        LoteDTO loteDTO = loteMapper.toDto(lote);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIngresoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lote.setFechaIngreso(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaVencimientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lote.setFechaVencimiento(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoLoteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lote.setCodigoLote(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFacturaProveedorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lote.setFacturaProveedor(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCantidadIngresadaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lote.setCantidadIngresada(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLotes() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        // Get all the loteList
        restLoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].codigoLote").value(hasItem(DEFAULT_CODIGO_LOTE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facturaProveedor").value(hasItem(DEFAULT_FACTURA_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].cantidadIngresada").value(hasItem(DEFAULT_CANTIDAD_INGRESADA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLotesWithEagerRelationshipsIsEnabled() throws Exception {
        when(loteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLoteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(loteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLotesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(loteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLoteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(loteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLote() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        // Get the lote
        restLoteMockMvc
            .perform(get(ENTITY_API_URL_ID, lote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.codigoLote").value(DEFAULT_CODIGO_LOTE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.facturaProveedor").value(DEFAULT_FACTURA_PROVEEDOR))
            .andExpect(jsonPath("$.cantidadIngresada").value(DEFAULT_CANTIDAD_INGRESADA));
    }

    @Test
    @Transactional
    void getNonExistingLote() throws Exception {
        // Get the lote
        restLoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLote() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lote
        Lote updatedLote = loteRepository.findById(lote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLote are not directly saved in db
        em.detach(updatedLote);
        updatedLote
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .codigoLote(UPDATED_CODIGO_LOTE)
            .descripcion(UPDATED_DESCRIPCION)
            .facturaProveedor(UPDATED_FACTURA_PROVEEDOR)
            .cantidadIngresada(UPDATED_CANTIDAD_INGRESADA);
        LoteDTO loteDTO = loteMapper.toDto(updatedLote);

        restLoteMockMvc
            .perform(put(ENTITY_API_URL_ID, loteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isOk());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLoteToMatchAllProperties(updatedLote);
    }

    @Test
    @Transactional
    void putNonExistingLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(put(ENTITY_API_URL_ID, loteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(loteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoteWithPatch() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lote using partial update
        Lote partialUpdatedLote = new Lote();
        partialUpdatedLote.setId(lote.getId());

        partialUpdatedLote
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidadIngresada(UPDATED_CANTIDAD_INGRESADA);

        restLoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLote))
            )
            .andExpect(status().isOk());

        // Validate the Lote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLote, lote), getPersistedLote(lote));
    }

    @Test
    @Transactional
    void fullUpdateLoteWithPatch() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lote using partial update
        Lote partialUpdatedLote = new Lote();
        partialUpdatedLote.setId(lote.getId());

        partialUpdatedLote
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .codigoLote(UPDATED_CODIGO_LOTE)
            .descripcion(UPDATED_DESCRIPCION)
            .facturaProveedor(UPDATED_FACTURA_PROVEEDOR)
            .cantidadIngresada(UPDATED_CANTIDAD_INGRESADA);

        restLoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLote))
            )
            .andExpect(status().isOk());

        // Validate the Lote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoteUpdatableFieldsEquals(partialUpdatedLote, getPersistedLote(partialUpdatedLote));
    }

    @Test
    @Transactional
    void patchNonExistingLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loteDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(loteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(loteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lote.setId(longCount.incrementAndGet());

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(loteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLote() throws Exception {
        // Initialize the database
        insertedLote = loteRepository.saveAndFlush(lote);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lote
        restLoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, lote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return loteRepository.count();
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

    protected Lote getPersistedLote(Lote lote) {
        return loteRepository.findById(lote.getId()).orElseThrow();
    }

    protected void assertPersistedLoteToMatchAllProperties(Lote expectedLote) {
        assertLoteAllPropertiesEquals(expectedLote, getPersistedLote(expectedLote));
    }

    protected void assertPersistedLoteToMatchUpdatableProperties(Lote expectedLote) {
        assertLoteAllUpdatablePropertiesEquals(expectedLote, getPersistedLote(expectedLote));
    }
}
