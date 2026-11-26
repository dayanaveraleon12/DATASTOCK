package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.CuentaTestSamples.*;
import static com.mycompany.datastock.domain.EvidenciaSalidaTestSamples.*;
import static com.mycompany.datastock.domain.HistorialErrorTestSamples.*;
import static com.mycompany.datastock.domain.TipoDocumentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CuentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuenta.class);
        Cuenta cuenta1 = getCuentaSample1();
        Cuenta cuenta2 = new Cuenta();
        assertThat(cuenta1).isNotEqualTo(cuenta2);

        cuenta2.setId(cuenta1.getId());
        assertThat(cuenta1).isEqualTo(cuenta2);

        cuenta2 = getCuentaSample2();
        assertThat(cuenta1).isNotEqualTo(cuenta2);
    }

    @Test
    void tipoDocumentoTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        TipoDocumento tipoDocumentoBack = getTipoDocumentoRandomSampleGenerator();

        cuenta.setTipoDocumento(tipoDocumentoBack);
        assertThat(cuenta.getTipoDocumento()).isEqualTo(tipoDocumentoBack);

        cuenta.tipoDocumento(null);
        assertThat(cuenta.getTipoDocumento()).isNull();
    }

    @Test
    void historialErroresTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        HistorialError historialErrorBack = getHistorialErrorRandomSampleGenerator();

        cuenta.addHistorialErrores(historialErrorBack);
        assertThat(cuenta.getHistorialErroreses()).containsOnly(historialErrorBack);
        assertThat(historialErrorBack.getCuenta()).isEqualTo(cuenta);

        cuenta.removeHistorialErrores(historialErrorBack);
        assertThat(cuenta.getHistorialErroreses()).doesNotContain(historialErrorBack);
        assertThat(historialErrorBack.getCuenta()).isNull();

        cuenta.historialErroreses(new HashSet<>(Set.of(historialErrorBack)));
        assertThat(cuenta.getHistorialErroreses()).containsOnly(historialErrorBack);
        assertThat(historialErrorBack.getCuenta()).isEqualTo(cuenta);

        cuenta.setHistorialErroreses(new HashSet<>());
        assertThat(cuenta.getHistorialErroreses()).doesNotContain(historialErrorBack);
        assertThat(historialErrorBack.getCuenta()).isNull();
    }

    @Test
    void cuentaSitiosTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        CuentaSitio cuentaSitioBack = getCuentaSitioRandomSampleGenerator();

        cuenta.addCuentaSitios(cuentaSitioBack);
        assertThat(cuenta.getCuentaSitioses()).containsOnly(cuentaSitioBack);
        assertThat(cuentaSitioBack.getCuenta()).isEqualTo(cuenta);

        cuenta.removeCuentaSitios(cuentaSitioBack);
        assertThat(cuenta.getCuentaSitioses()).doesNotContain(cuentaSitioBack);
        assertThat(cuentaSitioBack.getCuenta()).isNull();

        cuenta.cuentaSitioses(new HashSet<>(Set.of(cuentaSitioBack)));
        assertThat(cuenta.getCuentaSitioses()).containsOnly(cuentaSitioBack);
        assertThat(cuentaSitioBack.getCuenta()).isEqualTo(cuenta);

        cuenta.setCuentaSitioses(new HashSet<>());
        assertThat(cuenta.getCuentaSitioses()).doesNotContain(cuentaSitioBack);
        assertThat(cuentaSitioBack.getCuenta()).isNull();
    }

    @Test
    void evidenciaSalidasTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        EvidenciaSalida evidenciaSalidaBack = getEvidenciaSalidaRandomSampleGenerator();

        cuenta.addEvidenciaSalidas(evidenciaSalidaBack);
        assertThat(cuenta.getEvidenciaSalidases()).containsOnly(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getCuenta()).isEqualTo(cuenta);

        cuenta.removeEvidenciaSalidas(evidenciaSalidaBack);
        assertThat(cuenta.getEvidenciaSalidases()).doesNotContain(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getCuenta()).isNull();

        cuenta.evidenciaSalidases(new HashSet<>(Set.of(evidenciaSalidaBack)));
        assertThat(cuenta.getEvidenciaSalidases()).containsOnly(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getCuenta()).isEqualTo(cuenta);

        cuenta.setEvidenciaSalidases(new HashSet<>());
        assertThat(cuenta.getEvidenciaSalidases()).doesNotContain(evidenciaSalidaBack);
        assertThat(evidenciaSalidaBack.getCuenta()).isNull();
    }
}
