package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.EstadoProductoTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EstadoProductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoProducto.class);
        EstadoProducto estadoProducto1 = getEstadoProductoSample1();
        EstadoProducto estadoProducto2 = new EstadoProducto();
        assertThat(estadoProducto1).isNotEqualTo(estadoProducto2);

        estadoProducto2.setId(estadoProducto1.getId());
        assertThat(estadoProducto1).isEqualTo(estadoProducto2);

        estadoProducto2 = getEstadoProductoSample2();
        assertThat(estadoProducto1).isNotEqualTo(estadoProducto2);
    }

    @Test
    void productoSitiosTest() {
        EstadoProducto estadoProducto = getEstadoProductoRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        estadoProducto.addProductoSitios(productoSitioBack);
        assertThat(estadoProducto.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getEstadoProducto()).isEqualTo(estadoProducto);

        estadoProducto.removeProductoSitios(productoSitioBack);
        assertThat(estadoProducto.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getEstadoProducto()).isNull();

        estadoProducto.productoSitioses(new HashSet<>(Set.of(productoSitioBack)));
        assertThat(estadoProducto.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getEstadoProducto()).isEqualTo(estadoProducto);

        estadoProducto.setProductoSitioses(new HashSet<>());
        assertThat(estadoProducto.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getEstadoProducto()).isNull();
    }
}
