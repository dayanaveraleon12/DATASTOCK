package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CodigoTestSamples.*;
import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TrasladoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traslado.class);
        Traslado traslado1 = getTrasladoSample1();
        Traslado traslado2 = new Traslado();
        assertThat(traslado1).isNotEqualTo(traslado2);

        traslado2.setId(traslado1.getId());
        assertThat(traslado1).isEqualTo(traslado2);

        traslado2 = getTrasladoSample2();
        assertThat(traslado1).isNotEqualTo(traslado2);
    }

    @Test
    void cuentaSitioTest() {
        Traslado traslado = getTrasladoRandomSampleGenerator();
        CuentaSitio cuentaSitioBack = getCuentaSitioRandomSampleGenerator();

        traslado.setCuentaSitio(cuentaSitioBack);
        assertThat(traslado.getCuentaSitio()).isEqualTo(cuentaSitioBack);

        traslado.cuentaSitio(null);
        assertThat(traslado.getCuentaSitio()).isNull();
    }

    @Test
    void sitioDestinoTest() {
        Traslado traslado = getTrasladoRandomSampleGenerator();
        Sitio sitioBack = getSitioRandomSampleGenerator();

        traslado.setSitioDestino(sitioBack);
        assertThat(traslado.getSitioDestino()).isEqualTo(sitioBack);

        traslado.sitioDestino(null);
        assertThat(traslado.getSitioDestino()).isNull();
    }

    @Test
    void codigoTest() {
        Traslado traslado = getTrasladoRandomSampleGenerator();
        Codigo codigoBack = getCodigoRandomSampleGenerator();

        traslado.setCodigo(codigoBack);
        assertThat(traslado.getCodigo()).isEqualTo(codigoBack);

        traslado.codigo(null);
        assertThat(traslado.getCodigo()).isNull();
    }

    @Test
    void trasladoProductoSitiosTest() {
        Traslado traslado = getTrasladoRandomSampleGenerator();
        TrasladoProductoSitio trasladoProductoSitioBack = getTrasladoProductoSitioRandomSampleGenerator();

        traslado.addTrasladoProductoSitios(trasladoProductoSitioBack);
        assertThat(traslado.getTrasladoProductoSitioses()).containsOnly(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getTraslado()).isEqualTo(traslado);

        traslado.removeTrasladoProductoSitios(trasladoProductoSitioBack);
        assertThat(traslado.getTrasladoProductoSitioses()).doesNotContain(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getTraslado()).isNull();

        traslado.trasladoProductoSitioses(new HashSet<>(Set.of(trasladoProductoSitioBack)));
        assertThat(traslado.getTrasladoProductoSitioses()).containsOnly(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getTraslado()).isEqualTo(traslado);

        traslado.setTrasladoProductoSitioses(new HashSet<>());
        assertThat(traslado.getTrasladoProductoSitioses()).doesNotContain(trasladoProductoSitioBack);
        assertThat(trasladoProductoSitioBack.getTraslado()).isNull();
    }
}
