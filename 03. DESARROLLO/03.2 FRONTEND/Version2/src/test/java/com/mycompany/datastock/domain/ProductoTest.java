package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CategoriaTestSamples.*;
import static com.mycompany.datastock.domain.LoteTestSamples.*;
import static com.mycompany.datastock.domain.MarcaTestSamples.*;
import static com.mycompany.datastock.domain.PresentacionTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = getProductoSample1();
        Producto producto2 = new Producto();
        assertThat(producto1).isNotEqualTo(producto2);

        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);

        producto2 = getProductoSample2();
        assertThat(producto1).isNotEqualTo(producto2);
    }

    @Test
    void categoriaTest() {
        Producto producto = getProductoRandomSampleGenerator();
        Categoria categoriaBack = getCategoriaRandomSampleGenerator();

        producto.setCategoria(categoriaBack);
        assertThat(producto.getCategoria()).isEqualTo(categoriaBack);

        producto.categoria(null);
        assertThat(producto.getCategoria()).isNull();
    }

    @Test
    void presentacionTest() {
        Producto producto = getProductoRandomSampleGenerator();
        Presentacion presentacionBack = getPresentacionRandomSampleGenerator();

        producto.setPresentacion(presentacionBack);
        assertThat(producto.getPresentacion()).isEqualTo(presentacionBack);

        producto.presentacion(null);
        assertThat(producto.getPresentacion()).isNull();
    }

    @Test
    void marcaTest() {
        Producto producto = getProductoRandomSampleGenerator();
        Marca marcaBack = getMarcaRandomSampleGenerator();

        producto.setMarca(marcaBack);
        assertThat(producto.getMarca()).isEqualTo(marcaBack);

        producto.marca(null);
        assertThat(producto.getMarca()).isNull();
    }

    @Test
    void lotesTest() {
        Producto producto = getProductoRandomSampleGenerator();
        Lote loteBack = getLoteRandomSampleGenerator();

        producto.addLotes(loteBack);
        assertThat(producto.getLoteses()).containsOnly(loteBack);
        assertThat(loteBack.getProducto()).isEqualTo(producto);

        producto.removeLotes(loteBack);
        assertThat(producto.getLoteses()).doesNotContain(loteBack);
        assertThat(loteBack.getProducto()).isNull();

        producto.loteses(new HashSet<>(Set.of(loteBack)));
        assertThat(producto.getLoteses()).containsOnly(loteBack);
        assertThat(loteBack.getProducto()).isEqualTo(producto);

        producto.setLoteses(new HashSet<>());
        assertThat(producto.getLoteses()).doesNotContain(loteBack);
        assertThat(loteBack.getProducto()).isNull();
    }

    @Test
    void productoSitiosTest() {
        Producto producto = getProductoRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        producto.addProductoSitios(productoSitioBack);
        assertThat(producto.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getProducto()).isEqualTo(producto);

        producto.removeProductoSitios(productoSitioBack);
        assertThat(producto.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getProducto()).isNull();

        producto.productoSitioses(new HashSet<>(Set.of(productoSitioBack)));
        assertThat(producto.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getProducto()).isEqualTo(producto);

        producto.setProductoSitioses(new HashSet<>());
        assertThat(producto.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getProducto()).isNull();
    }
}
