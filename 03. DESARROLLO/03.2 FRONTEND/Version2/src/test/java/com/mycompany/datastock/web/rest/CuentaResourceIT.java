package com.mycompany.datastock.web.rest;

import static com.mycompany.datastock.domain.CuentaAsserts.*;
import static com.mycompany.datastock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.datastock.IntegrationTest;
import com.mycompany.datastock.domain.Cuenta;
import com.mycompany.datastock.domain.TipoDocumento;
import com.mycompany.datastock.domain.User;
import com.mycompany.datastock.repository.CuentaRepository;
import com.mycompany.datastock.repository.UserRepository;
import com.mycompany.datastock.service.CuentaService;
import com.mycompany.datastock.service.dto.CuentaDTO;
import com.mycompany.datastock.service.mapper.CuentaMapper;
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
 * Integration tests for the {@link CuentaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CuentaResourceIT {

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_EPS = "AAAAAAAAAA";
    private static final String UPDATED_EPS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_EMERGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_EMERGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTEZCO_EMERGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PARENTEZCO_EMERGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_EMERGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMERGENCIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cuentas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private CuentaRepository cuentaRepositoryMock;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Mock
    private CuentaService cuentaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuentaMockMvc;

    private Cuenta cuenta;

    private Cuenta insertedCuenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuenta createEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .primerNombre(DEFAULT_PRIMER_NOMBRE)
            .segundoNombre(DEFAULT_SEGUNDO_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .numeroCelular(DEFAULT_NUMERO_CELULAR)
            .eps(DEFAULT_EPS)
            .contactoEmergencia(DEFAULT_CONTACTO_EMERGENCIA)
            .parentezcoEmergencia(DEFAULT_PARENTEZCO_EMERGENCIA)
            .nombreEmergencia(DEFAULT_NOMBRE_EMERGENCIA);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        cuenta.setUser(user);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createEntity();
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).getFirst();
        }
        cuenta.setTipoDocumento(tipoDocumento);
        return cuenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuenta createUpdatedEntity(EntityManager em) {
        Cuenta updatedCuenta = new Cuenta()
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .numeroCelular(UPDATED_NUMERO_CELULAR)
            .eps(UPDATED_EPS)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .parentezcoEmergencia(UPDATED_PARENTEZCO_EMERGENCIA)
            .nombreEmergencia(UPDATED_NOMBRE_EMERGENCIA);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedCuenta.setUser(user);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity();
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).getFirst();
        }
        updatedCuenta.setTipoDocumento(tipoDocumento);
        return updatedCuenta;
    }

    @BeforeEach
    void initTest() {
        cuenta = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCuenta != null) {
            cuentaRepository.delete(insertedCuenta);
            insertedCuenta = null;
        }
    }

    @Test
    @Transactional
    void createCuenta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);
        var returnedCuentaDTO = om.readValue(
            restCuentaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CuentaDTO.class
        );

        // Validate the Cuenta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCuenta = cuentaMapper.toEntity(returnedCuentaDTO);
        assertCuentaUpdatableFieldsEquals(returnedCuenta, getPersistedCuenta(returnedCuenta));

        insertedCuenta = returnedCuenta;
    }

    @Test
    @Transactional
    void createCuentaWithExistingId() throws Exception {
        // Create the Cuenta with an existing ID
        cuenta.setId(1L);
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroDocumentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setNumeroDocumento(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setPrimerNombre(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setPrimerApellido(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroCelularIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setNumeroCelular(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setEps(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactoEmergenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setContactoEmergencia(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParentezcoEmergenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setParentezcoEmergencia(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreEmergenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuenta.setNombreEmergencia(null);

        // Create the Cuenta, which fails.
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        restCuentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCuentas() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList
        restCuentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].primerNombre").value(hasItem(DEFAULT_PRIMER_NOMBRE)))
            .andExpect(jsonPath("$.[*].segundoNombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].numeroCelular").value(hasItem(DEFAULT_NUMERO_CELULAR)))
            .andExpect(jsonPath("$.[*].eps").value(hasItem(DEFAULT_EPS)))
            .andExpect(jsonPath("$.[*].contactoEmergencia").value(hasItem(DEFAULT_CONTACTO_EMERGENCIA)))
            .andExpect(jsonPath("$.[*].parentezcoEmergencia").value(hasItem(DEFAULT_PARENTEZCO_EMERGENCIA)))
            .andExpect(jsonPath("$.[*].nombreEmergencia").value(hasItem(DEFAULT_NOMBRE_EMERGENCIA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCuentasWithEagerRelationshipsIsEnabled() throws Exception {
        when(cuentaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCuentaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cuentaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCuentasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cuentaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCuentaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cuentaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCuenta() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        // Get the cuenta
        restCuentaMockMvc
            .perform(get(ENTITY_API_URL_ID, cuenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuenta.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.primerNombre").value(DEFAULT_PRIMER_NOMBRE))
            .andExpect(jsonPath("$.segundoNombre").value(DEFAULT_SEGUNDO_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.numeroCelular").value(DEFAULT_NUMERO_CELULAR))
            .andExpect(jsonPath("$.eps").value(DEFAULT_EPS))
            .andExpect(jsonPath("$.contactoEmergencia").value(DEFAULT_CONTACTO_EMERGENCIA))
            .andExpect(jsonPath("$.parentezcoEmergencia").value(DEFAULT_PARENTEZCO_EMERGENCIA))
            .andExpect(jsonPath("$.nombreEmergencia").value(DEFAULT_NOMBRE_EMERGENCIA));
    }

    @Test
    @Transactional
    void getNonExistingCuenta() throws Exception {
        // Get the cuenta
        restCuentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCuenta() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuenta
        Cuenta updatedCuenta = cuentaRepository.findById(cuenta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCuenta are not directly saved in db
        em.detach(updatedCuenta);
        updatedCuenta
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .numeroCelular(UPDATED_NUMERO_CELULAR)
            .eps(UPDATED_EPS)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .parentezcoEmergencia(UPDATED_PARENTEZCO_EMERGENCIA)
            .nombreEmergencia(UPDATED_NOMBRE_EMERGENCIA);
        CuentaDTO cuentaDTO = cuentaMapper.toDto(updatedCuenta);

        restCuentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCuentaToMatchAllProperties(updatedCuenta);
    }

    @Test
    @Transactional
    void putNonExistingCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuentaWithPatch() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuenta using partial update
        Cuenta partialUpdatedCuenta = new Cuenta();
        partialUpdatedCuenta.setId(cuenta.getId());

        partialUpdatedCuenta
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .numeroCelular(UPDATED_NUMERO_CELULAR)
            .eps(UPDATED_EPS)
            .nombreEmergencia(UPDATED_NOMBRE_EMERGENCIA);

        restCuentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuenta))
            )
            .andExpect(status().isOk());

        // Validate the Cuenta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCuenta, cuenta), getPersistedCuenta(cuenta));
    }

    @Test
    @Transactional
    void fullUpdateCuentaWithPatch() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuenta using partial update
        Cuenta partialUpdatedCuenta = new Cuenta();
        partialUpdatedCuenta.setId(cuenta.getId());

        partialUpdatedCuenta
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .numeroCelular(UPDATED_NUMERO_CELULAR)
            .eps(UPDATED_EPS)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .parentezcoEmergencia(UPDATED_PARENTEZCO_EMERGENCIA)
            .nombreEmergencia(UPDATED_NOMBRE_EMERGENCIA);

        restCuentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuenta))
            )
            .andExpect(status().isOk());

        // Validate the Cuenta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentaUpdatableFieldsEquals(partialUpdatedCuenta, getPersistedCuenta(partialUpdatedCuenta));
    }

    @Test
    @Transactional
    void patchNonExistingCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuentaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuenta.setId(longCount.incrementAndGet());

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cuentaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuenta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuenta() throws Exception {
        // Initialize the database
        insertedCuenta = cuentaRepository.saveAndFlush(cuenta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cuenta
        restCuentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cuentaRepository.count();
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

    protected Cuenta getPersistedCuenta(Cuenta cuenta) {
        return cuentaRepository.findById(cuenta.getId()).orElseThrow();
    }

    protected void assertPersistedCuentaToMatchAllProperties(Cuenta expectedCuenta) {
        assertCuentaAllPropertiesEquals(expectedCuenta, getPersistedCuenta(expectedCuenta));
    }

    protected void assertPersistedCuentaToMatchUpdatableProperties(Cuenta expectedCuenta) {
        assertCuentaAllUpdatablePropertiesEquals(expectedCuenta, getPersistedCuenta(expectedCuenta));
    }
}
