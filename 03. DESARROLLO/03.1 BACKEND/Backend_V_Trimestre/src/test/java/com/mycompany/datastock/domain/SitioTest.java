package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.EvidenciaSalidaTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.RemisionTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SitioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sitio.class);
        Sitio sitio1 = getSitioSample1();
        Sitio sitio2 = new Sitio();
        assertThat(sitio1).isNotEqualTo(sitio2);

        sitio2.setId(sitio1.getId());
        assertThat(sitio1).isEqualTo(sitio2);

        sitio2 = getSitioSample2();
        assertThat(sitio1).isNotEqualTo(sitio2);
    }

    @Test
    void productoSitiosTest() {
        Sitio sitio = getSitioRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        sitio.addProductoSitios(productoSitioBack);
        assertThat(sitio.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getSitio()).isEqualTo(sitio);

        sitio.removeProductoSitios(productoSitioBack);
        assertThat(sitio.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getSitio()).isNull();

        sitio.productoSitioses(new HashSet<>(Set.of(productoSitioBack)));
        assertThat(sitio.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getSitio()).isEqualTo(sitio);

        sitio.setProductoSitioses(new HashSet<>());
        assertThat(sitio.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getSitio()).isNull();
    }

    @Test
    void cuentaSitiosTest() {
        Sitio sitio = getSitioRandomSampleGenerator();
        CuentaSitio cuentaSitioBack = getCuentaSitioRandomSampleGenerator();

        sitio.addCuentaSitios(cuentaSitioBack);
        assertThat(sitio.getCuentaSitioses()).containsOnly(cuentaSitioBack);
        assertThat(cuentaSitioBack.getSitio()).isEqualTo(sitio);

        sitio.removeCuentaSitios(cuentaSitioBack);
        assertThat(sitio.getCuentaSitioses()).doesNotContain(cuentaSitioBack);
        assertThat(cuentaSitioBack.getSitio()).isNull();

        sitio.cuentaSitioses(new HashSet<>(Set.of(cuentaSitioBack)));
        assertThat(sitio.getCuentaSitioses()).containsOnly(cuentaSitioBack);
        assertThat(cuentaSitioBack.getSitio()).isEqualTo(sitio);

        sitio.setCuentaSitioses(new HashSet<>());
        assertThat(sitio.getCuentaSitioses()).doesNotContain(cuentaSitioBack);
        assertThat(cuentaSitioBack.getSitio()).isNull();
    }

    @Test
    void remisionesDestinoTest() {
        Sitio sitio = getSitioRandomSampleGenerator();
        Remision remisionBack = getRemisionRandomSampleGenerator();

        sitio.addRemisionesDestino(remisionBack);
        assertThat(sitio.getRemisionesDestinos()).containsOnly(remisionBack);
        assertThat(remisionBack.getSitioDestino()).isEqualTo(sitio);

        sitio.removeRemisionesDestino(remisionBack);
        assertThat(sitio.getRemisionesDestinos()).doesNotContain(remisionBack);
        assertThat(remisionBack.getSitioDestino()).isNull();

        sitio.remisionesDestinos(new HashSet<>(Set.of(remisionBack)));
        assertThat(sitio.getRemisionesDestinos()).containsOnly(remisionBack);
        assertThat(remisionBack.getSitioDestino()).isEqualTo(sitio);

        sitio.setRemisionesDestinos(new HashSet<>());
        assertThat(sitio.getRemisionesDestinos()).doesNotContain(remisionBack);
        assertThat(remisionBack.getSitioDestino()).isNull();
    }

    @Test
    void trasladosDestinoTest() {
        Sitio sitio = getSitioRandomSampleGenerator();
        Traslado trasladoBack = getTrasladoRandomSampleGenerator();

        sitio.addTrasladosDestino(trasladoBack);
        assertThat(sitio.getTrasladosDestinos()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getSitioDestino()).isEqualTo(sitio);

        sitio.removeTrasladosDestino(trasladoBack);
        assertThat(sitio.getTrasladosDestinos()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getSitioDestino()).isNull();

        sitio.trasladosDestinos(new HashSet<>(Set.of(trasladoBack)));
        assertThat(sitio.getTrasladosDestinos()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getSitioDestino()).isEqualTo(sitio);

        sitio.setTrasladosDestinos(new HashSet<>());
        assertThat(sitio.getTrasladosDestinos()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getSitioDestino()).isNull();
    }

    @Test
    void evidenciaSalidasTest() {
        Sitio sitio = getSitioRandomSampleGenerator();
        EvidenciaSalida evidenciaSalidaBack = getEvidenciaSalidaRandomSampleGenerator();

        sitio.addEvidenciaSalidas(evidenciaSalidaBack);
        assertThat(sitio.getEvidenciaSalidases()).containsOnly(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getSitioSubida()).isEqualTo(sitio);

        sitio.removeEvidenciaSalidas(evidenciaSalidaBack);
        assertThat(sitio.getEvidenciaSalidases()).doesNotContain(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getSitioSubida()).isNull();

        sitio.evidenciaSalidases(new HashSet<>(Set.of(evidenciaSalidaBack)));
        assertThat(sitio.getEvidenciaSalidases()).containsOnly(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getSitioSubida()).isEqualTo(sitio);

        sitio.setEvidenciaSalidases(new HashSet<>());
        assertThat(sitio.getEvidenciaSalidases()).doesNotContain(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getSitioSubida()).isNull();
    }
}
