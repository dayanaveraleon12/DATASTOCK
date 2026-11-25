package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.MarcaTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MarcaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marca.class);
        Marca marca1 = getMarcaSample1();
        Marca marca2 = new Marca();
        assertThat(marca1).isNotEqualTo(marca2);

        marca2.setId(marca1.getId());
        assertThat(marca1).isEqualTo(marca2);

        marca2 = getMarcaSample2();
        assertThat(marca1).isNotEqualTo(marca2);
    }

    @Test
    void productosTest() {
        Marca marca = getMarcaRandomSampleGenerator();
        Producto productoBack = getProductoRandomSampleGenerator();

        marca.addProductos(productoBack);
        assertThat(marca.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getMarca()).isEqualTo(marca);

        marca.removeProductos(productoBack);
        assertThat(marca.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getMarca()).isNull();

        marca.productoses(new HashSet<>(Set.of(productoBack)));
        assertThat(marca.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getMarca()).isEqualTo(marca);

        marca.setProductoses(new HashSet<>());
        assertThat(marca.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getMarca()).isNull();
    }
}
