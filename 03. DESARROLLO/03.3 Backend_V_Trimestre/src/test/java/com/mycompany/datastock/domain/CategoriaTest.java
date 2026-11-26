package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CategoriaTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoria.class);
        Categoria categoria1 = getCategoriaSample1();
        Categoria categoria2 = new Categoria();
        assertThat(categoria1).isNotEqualTo(categoria2);

        categoria2.setId(categoria1.getId());
        assertThat(categoria1).isEqualTo(categoria2);

        categoria2 = getCategoriaSample2();
        assertThat(categoria1).isNotEqualTo(categoria2);
    }

    @Test
    void productosTest() {
        Categoria categoria = getCategoriaRandomSampleGenerator();
        Producto productoBack = getProductoRandomSampleGenerator();

        categoria.addProductos(productoBack);
        assertThat(categoria.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getCategoria()).isEqualTo(categoria);

        categoria.removeProductos(productoBack);
        assertThat(categoria.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getCategoria()).isNull();

        categoria.productoses(new HashSet<>(Set.of(productoBack)));
        assertThat(categoria.getProductoses()).containsOnly(productoBack);
        assertThat(productoBack.getCategoria()).isEqualTo(categoria);

        categoria.setProductoses(new HashSet<>());
        assertThat(categoria.getProductoses()).doesNotContain(productoBack);
        assertThat(productoBack.getCategoria()).isNull();
    }
}
