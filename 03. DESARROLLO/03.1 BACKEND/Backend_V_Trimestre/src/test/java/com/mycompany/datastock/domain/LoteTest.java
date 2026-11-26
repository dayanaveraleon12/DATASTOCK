package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.LoteTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.ProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lote.class);
        Lote lote1 = getLoteSample1();
        Lote lote2 = new Lote();
        assertThat(lote1).isNotEqualTo(lote2);

        lote2.setId(lote1.getId());
        assertThat(lote1).isEqualTo(lote2);

        lote2 = getLoteSample2();
        assertThat(lote1).isNotEqualTo(lote2);
    }

    @Test
    void productoTest() {
        Lote lote = getLoteRandomSampleGenerator();
        Producto productoBack = getProductoRandomSampleGenerator();

        lote.setProducto(productoBack);
        assertThat(lote.getProducto()).isEqualTo(productoBack);

        lote.producto(null);
        assertThat(lote.getProducto()).isNull();
    }

    @Test
    void productoSitiosTest() {
        Lote lote = getLoteRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        lote.addProductoSitios(productoSitioBack);
        assertThat(lote.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getLote()).isEqualTo(lote);

        lote.removeProductoSitios(productoSitioBack);
        assertThat(lote.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getLote()).isNull();

        lote.productoSitioses(new HashSet<>(Set.of(productoSitioBack)));
        assertThat(lote.getProductoSitioses()).containsOnly(productoSitioBack);
        assertThat(productoSitioBack.getLote()).isEqualTo(lote);

        lote.setProductoSitioses(new HashSet<>());
        assertThat(lote.getProductoSitioses()).doesNotContain(productoSitioBack);
        assertThat(productoSitioBack.getLote()).isNull();
    }
}
