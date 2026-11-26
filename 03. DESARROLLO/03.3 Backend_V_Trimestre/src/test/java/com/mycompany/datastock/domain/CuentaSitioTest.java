package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CodigoTestSamples.*;
import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.CuentaTestSamples.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CuentaSitioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaSitio.class);
        CuentaSitio cuentaSitio1 = getCuentaSitioSample1();
        CuentaSitio cuentaSitio2 = new CuentaSitio();
        assertThat(cuentaSitio1).isNotEqualTo(cuentaSitio2);

        cuentaSitio2.setId(cuentaSitio1.getId());
        assertThat(cuentaSitio1).isEqualTo(cuentaSitio2);

        cuentaSitio2 = getCuentaSitioSample2();
        assertThat(cuentaSitio1).isNotEqualTo(cuentaSitio2);
    }

    @Test
    void cuentaTest() {
        CuentaSitio cuentaSitio = getCuentaSitioRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        cuentaSitio.setCuenta(cuentaBack);
        assertThat(cuentaSitio.getCuenta()).isEqualTo(cuentaBack);

        cuentaSitio.cuenta(null);
        assertThat(cuentaSitio.getCuenta()).isNull();
    }

    @Test
    void sitioTest() {
        CuentaSitio cuentaSitio = getCuentaSitioRandomSampleGenerator();
        Sitio sitioBack = getSitioRandomSampleGenerator();

        cuentaSitio.setSitio(sitioBack);
        assertThat(cuentaSitio.getSitio()).isEqualTo(sitioBack);

        cuentaSitio.sitio(null);
        assertThat(cuentaSitio.getSitio()).isNull();
    }

    @Test
    void codigosTest() {
        CuentaSitio cuentaSitio = getCuentaSitioRandomSampleGenerator();
        Codigo codigoBack = getCodigoRandomSampleGenerator();

        cuentaSitio.addCodigos(codigoBack);
        assertThat(cuentaSitio.getCodigoses()).containsOnly(codigoBack);
        assertThat(codigoBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.removeCodigos(codigoBack);
        assertThat(cuentaSitio.getCodigoses()).doesNotContain(codigoBack);
        assertThat(codigoBack.getCuentaSitio()).isNull();

        cuentaSitio.codigoses(new HashSet<>(Set.of(codigoBack)));
        assertThat(cuentaSitio.getCodigoses()).containsOnly(codigoBack);
        assertThat(codigoBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.setCodigoses(new HashSet<>());
        assertThat(cuentaSitio.getCodigoses()).doesNotContain(codigoBack);
        assertThat(codigoBack.getCuentaSitio()).isNull();
    }

    @Test
    void trasladosTest() {
        CuentaSitio cuentaSitio = getCuentaSitioRandomSampleGenerator();
        Traslado trasladoBack = getTrasladoRandomSampleGenerator();

        cuentaSitio.addTraslados(trasladoBack);
        assertThat(cuentaSitio.getTrasladoses()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.removeTraslados(trasladoBack);
        assertThat(cuentaSitio.getTrasladoses()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getCuentaSitio()).isNull();

        cuentaSitio.trasladoses(new HashSet<>(Set.of(trasladoBack)));
        assertThat(cuentaSitio.getTrasladoses()).containsOnly(trasladoBack);
        assertThat(trasladoBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.setTrasladoses(new HashSet<>());
        assertThat(cuentaSitio.getTrasladoses()).doesNotContain(trasladoBack);
        assertThat(trasladoBack.getCuentaSitio()).isNull();
    }

    @Test
    void salidasTest() {
        CuentaSitio cuentaSitio = getCuentaSitioRandomSampleGenerator();
        Salida salidaBack = getSalidaRandomSampleGenerator();

        cuentaSitio.addSalidas(salidaBack);
        assertThat(cuentaSitio.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.removeSalidas(salidaBack);
        assertThat(cuentaSitio.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getCuentaSitio()).isNull();

        cuentaSitio.salidases(new HashSet<>(Set.of(salidaBack)));
        assertThat(cuentaSitio.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getCuentaSitio()).isEqualTo(cuentaSitio);

        cuentaSitio.setSalidases(new HashSet<>());
        assertThat(cuentaSitio.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getCuentaSitio()).isNull();
    }
}
