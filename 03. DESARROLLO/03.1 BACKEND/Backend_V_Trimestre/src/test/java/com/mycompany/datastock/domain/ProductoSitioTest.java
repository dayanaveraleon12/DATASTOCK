package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.EstadoProductoTestSamples.*;
import static com.mycompany.datastock.domain.LoteTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static com.mycompany.datastock.domain.RemisionTestSamples.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoProductoSitioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductoSitioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoSitio.class);
        ProductoSitio productoSitio1 = getProductoSitioSample1();
        ProductoSitio productoSitio2 = new ProductoSitio();
        assertThat(productoSitio1).isNotEqualTo(productoSitio2);

        productoSitio2.setId(productoSitio1.getId());
        assertThat(productoSitio1).isEqualTo(productoSitio2);

        productoSitio2 = getProductoSitioSample2();
        assertThat(productoSitio1).isNotEqualTo(productoSitio2);
    }

    @Test
    void productoTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        Producto productoBack = getProductoRandomSampleGenerator();

        productoSitio.setProducto(productoBack);
        assertThat(productoSitio.getProducto()).isEqualTo(productoBack);

        productoSitio.producto(null);
        assertThat(productoSitio.getProducto()).isNull();
    }

    @Test
    void sitioTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        Sitio sitioBack = getSitioRandomSampleGenerator();

        productoSitio.setSitio(sitioBack);
        assertThat(productoSitio.getSitio()).isEqualTo(sitioBack);

        productoSitio.sitio(null);
        assertThat(productoSitio.getSitio()).isNull();
    }

    @Test
    void loteTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        Lote loteBack = getLoteRandomSampleGenerator();

        productoSitio.setLote(loteBack);
        assertThat(productoSitio.getLote()).isEqualTo(loteBack);

        productoSitio.lote(null);
        assertThat(productoSitio.getLote()).isNull();
    }

    @Test
    void estadoProductoTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        EstadoProducto estadoProductoBack = getEstadoProductoRandomSampleGenerator();

        productoSitio.setEstadoProducto(estadoProductoBack);
        assertThat(productoSitio.getEstadoProducto()).isEqualTo(estadoProductoBack);

        productoSitio.estadoProducto(null);
        assertThat(productoSitio.getEstadoProducto()).isNull();
    }

    @Test
    void trasladoProductoSitiosTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        TrasladoProductoSitio trasladoProductoSitioBack = getTrasladoProductoSitioRandomSampleGenerator();

        productoSitio.addTrasladoProductoSitios(trasladoProductoSitioBack);
        assertThat(productoSitio.getTrasladoProductoSitioses()).containsOnly(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getProductoSitio()).isEqualTo(productoSitio);

        productoSitio.removeTrasladoProductoSitios(trasladoProductoSitioBack);
        assertThat(productoSitio.getTrasladoProductoSitioses()).doesNotContain(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getProductoSitio()).isNull();

        productoSitio.trasladoProductoSitioses(new HashSet<>(Set.of(trasladoProductoSitioBack)));
        assertThat(productoSitio.getTrasladoProductoSitioses()).containsOnly(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getProductoSitio()).isEqualTo(productoSitio);

        productoSitio.setTrasladoProductoSitioses(new HashSet<>());
        assertThat(productoSitio.getTrasladoProductoSitioses()).doesNotContain(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getProductoSitio()).isNull();
    }

    @Test
    void remisionesTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        Remision remisionBack = getRemisionRandomSampleGenerator();

        productoSitio.addRemisiones(remisionBack);
        assertThat(productoSitio.getRemisioneses()).containsOnly(remisionBack);
        assertThat(remisionBack.getProductoSitio()).isEqualTo(productoSitio);

        productoSitio.removeRemisiones(remisionBack);
        assertThat(productoSitio.getRemisioneses()).doesNotContain(remisionBack);
        assertThat(remisionBack.getProductoSitio()).isNull();

        productoSitio.remisioneses(new HashSet<>(Set.of(remisionBack)));
        assertThat(productoSitio.getRemisioneses()).containsOnly(remisionBack);
        assertThat(remisionBack.getProductoSitio()).isEqualTo(productoSitio);

        productoSitio.setRemisioneses(new HashSet<>());
        assertThat(productoSitio.getRemisioneses()).doesNotContain(remisionBack);
        assertThat(remisionBack.getProductoSitio()).isNull();
    }

    @Test
    void salidasTest() {
        ProductoSitio productoSitio = getProductoSitioRandomSampleGenerator();
        Salida salidaBack = getSalidaRandomSampleGenerator();

        productoSitio.addSalidas(salidaBack);
        assertThat(productoSitio.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getProductoSitioses()).containsOnly(productoSitio);

        productoSitio.removeSalidas(salidaBack);
        assertThat(productoSitio.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getProductoSitioses()).doesNotContain(productoSitio);

        productoSitio.salidases(new HashSet<>(Set.of(salidaBack)));
        assertThat(productoSitio.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getProductoSitioses()).containsOnly(productoSitio);

        productoSitio.setSalidases(new HashSet<>());
        assertThat(productoSitio.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getProductoSitioses()).doesNotContain(productoSitio);
    }
}
