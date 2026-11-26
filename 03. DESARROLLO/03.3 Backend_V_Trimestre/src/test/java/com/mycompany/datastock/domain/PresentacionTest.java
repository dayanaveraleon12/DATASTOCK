package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.PresentacionTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PresentacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presentacion.class);
        Presentacion presentacion1 = getPresentacionSample1();
        Presentacion presentacion2 = new Presentacion();
        assertThat(presentacion1).isNotEqualTo(presentacion2);

        presentacion2.setId(presentacion1.getId());
        assertThat(presentacion1).isEqualTo(presentacion2);

        presentacion2 = getPresentacionSample2();
        assertThat(presentacion1).isNotEqualTo(presentacion2);
    }

    @Test
    void productosTest() {
        Presentacion presentacion = getPresentacionRandomSampleGenerator();
        Producto productoBack = getProductoRandomSampleGenerator();

        presentacion.addProductos(productoBack);
        assertThat(presentacion.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getPresentacion()).isEqualTo(presentacion);

        presentacion.removeProductos(productoBack);
        assertThat(presentacion.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getPresentacion()).isNull();

        presentacion.productoses(new HashSet<>(Set.of(productoBack)));
        assertThat(presentacion.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getPresentacion()).isEqualTo(presentacion);

        presentacion.setProductoses(new HashSet<>());
        assertThat(presentacion.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getPresentacion()).isNull();
    }
}
