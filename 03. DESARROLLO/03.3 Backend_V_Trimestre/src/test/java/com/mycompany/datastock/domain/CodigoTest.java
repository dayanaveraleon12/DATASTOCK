package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CodigoTestSamples.*;
import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.RemisionTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CodigoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Codigo.class);
        Codigo codigo1 = getCodigoSample1();
        Codigo codigo2 = new Codigo();
        assertThat(codigo1).isNotEqualTo(codigo2);

        codigo2.setId(codigo1.getId());
        assertThat(codigo1).isEqualTo(codigo2);

        codigo2 = getCodigoSample2();
        assertThat(codigo1).isNotEqualTo(codigo2);
    }

    @Test
    void cuentaSitioTest() {
        Codigo codigo = getCodigoRandomSampleGenerator();
        CuentaSitio cuentaSitioBack = getCuentaSitioRandomSampleGenerator();

        codigo.setCuentaSitio(cuentaSitioBack);
        assertThat(codigo.getCuentaSitio()).isEqualTo(cuentaSitioBack);

        codigo.cuentaSitio(null);
        assertThat(codigo.getCuentaSitio()).isNull();
    }

    @Test
    void remisionesTest() {
        Codigo codigo = getCodigoRandomSampleGenerator();
        Remision remisionBack = getRemisionRandomSampleGenerator();

        codigo.addRemisiones(remisionBack);
        assertThat(codigo.getRemisioneses()).containsOnly(remisionBack);
        assertThat(remisionBack.getCodigo()).isEqualTo(codigo);

        codigo.removeRemisiones(remisionBack);
        assertThat(codigo.getRemisioneses()).doesNotContain(remisionBack);
        assertThat(remisionBack.getCodigo()).isNull();

        codigo.remisioneses(new HashSet<>(Set.of(remisionBack)));
        assertThat(codigo.getRemisioneses()).containsOnly(remisionBack);
        assertThat(remisionBack.getCodigo()).isEqualTo(codigo);

        codigo.setRemisioneses(new HashSet<>());
        assertThat(codigo.getRemisioneses()).doesNotContain(remisionBack);
        assertThat(remisionBack.getCodigo()).isNull();
    }

    @Test
    void trasladosTest() {
        Codigo codigo = getCodigoRandomSampleGenerator();
        Traslado trasladoBack = getTrasladoRandomSampleGenerator();

        codigo.addTraslados(trasladoBack);
        assertThat(codigo.getTrasladoses()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getCodigo()).isEqualTo(codigo);

        codigo.removeTraslados(trasladoBack);
        assertThat(codigo.getTrasladoses()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getCodigo()).isNull();

        codigo.trasladoses(new HashSet<>(Set.of(trasladoBack)));
        assertThat(codigo.getTrasladoses()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getCodigo()).isEqualTo(codigo);

        codigo.setTrasladoses(new HashSet<>());
        assertThat(codigo.getTrasladoses()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getCodigo()).isNull();
    }
}
