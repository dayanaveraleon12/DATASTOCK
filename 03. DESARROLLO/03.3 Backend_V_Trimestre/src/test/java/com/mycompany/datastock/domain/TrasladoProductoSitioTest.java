package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrasladoProductoSitioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrasladoProductoSitio.class);
        TrasladoProductoSitio trasladoProductoSitio1 = getTrasladoProductoSitioSample1();
        TrasladoProductoSitio trasladoProductoSitio2 = new TrasladoProductoSitio();
        assertThat(trasladoProductoSitio1).isNotEqualTo(trasladoProductoSitio2);

        trasladoProductoSitio2.setId(trasladoProductoSitio1.getId());
        assertThat(trasladoProductoSitio1).isEqualTo(trasladoProductoSitio2);

        trasladoProductoSitio2 = getTrasladoProductoSitioSample2();
        assertThat(trasladoProductoSitio1).isNotEqualTo(trasladoProductoSitio2);
    }

    @Test
    void trasladoTest() {
        TrasladoProductoSitio trasladoProductoSitio = getTrasladoProductoSitioRandomSampleGenerator();
        Traslado trasladoBack = getTrasladoRandomSampleGenerator();

        trasladoProductoSitio.setTraslado(trasladoBack);
        assertThat(trasladoProductoSitio.getTraslado()).isEqualTo(trasladoBack);

        trasladoProductoSitio.traslado(null);
        assertThat(trasladoProductoSitio.getTraslado()).isNull();
    }

    @Test
    void productoSitioTest() {
        TrasladoProductoSitio trasladoProductoSitio = getTrasladoProductoSitioRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        trasladoProductoSitio.setProductoSitio(productoSitioBack);
        assertThat(trasladoProductoSitio.getProductoSitio()).isEqualTo(productoSitioBack);

        trasladoProductoSitio.productoSitio(null);
        assertThat(trasladoProductoSitio.getProductoSitio()).isNull();
    }
}
