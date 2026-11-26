package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CuentaTestSamples.*;
import static com.mycompany.datastock.domain.HistorialErrorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistorialErrorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialError.class);
        HistorialError historialError1 = getHistorialErrorSample1();
        HistorialError historialError2 = new HistorialError();
        assertThat(historialError1).isNotEqualTo(historialError2);

        historialError2.setId(historialError1.getId());
        assertThat(historialError1).isEqualTo(historialError2);

        historialError2 = getHistorialErrorSample2();
        assertThat(historialError1).isNotEqualTo(historialError2);
    }

    @Test
    void cuentaTest() {
        HistorialError historialError = getHistorialErrorRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        historialError.setCuenta(cuentaBack);
        assertThat(historialError.getCuenta()).isEqualTo(cuentaBack);

        historialError.cuenta(null);
        assertThat(historialError.getCuenta()).isNull();
    }
}
