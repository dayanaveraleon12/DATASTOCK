package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CodigoTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.RemisionTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemisionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remision.class);
        Remision remision1 = getRemisionSample1();
        Remision remision2 = new Remision();
        assertThat(remision1).isNotEqualTo(remision2);

        remision2.setId(remision1.getId());
        assertThat(remision1).isEqualTo(remision2);

        remision2 = getRemisionSample2();
        assertThat(remision1).isNotEqualTo(remision2);
    }

    @Test
    void productoSitioTest() {
        Remision remision = getRemisionRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        remision.setProductoSitio(productoSitioBack);
        assertThat(remision.getProductoSitio()).isEqualTo(productoSitioBack);

        remision.productoSitio(null);
        assertThat(remision.getProductoSitio()).isNull();
    }

    @Test
    void codigoTest() {
        Remision remision = getRemisionRandomSampleGenerator();
        Codigo codigoBack = getCodigoRandomSampleGenerator();

        remision.setCodigo(codigoBack);
        assertThat(remision.getCodigo()).isEqualTo(codigoBack);

        remision.codigo(null);
        assertThat(remision.getCodigo()).isNull();
    }

    @Test
    void sitioDestinoTest() {
        Remision remision = getRemisionRandomSampleGenerator();
        Sitio sitioBack = getSitioRandomSampleGenerator();

        remision.setSitioDestino(sitioBack);
        assertThat(remision.getSitioDestino()).isEqualTo(sitioBack);

        remision.sitioDestino(null);
        assertThat(remision.getSitioDestino()).isNull();
    }
}
