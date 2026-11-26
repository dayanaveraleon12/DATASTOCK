package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CuentaTestSamples.*;
import static com.mycompany.datastock.domain.EvidenciaSalidaTestSamples.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvidenciaSalidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvidenciaSalida.class);
        EvidenciaSalida evidenciaSalida1 = getEvidenciaSalidaSample1();
        EvidenciaSalida evidenciaSalida2 = new EvidenciaSalida();
        assertThat(evidenciaSalida1).isNotEqualTo(evidenciaSalida2);

        evidenciaSalida2.setId(evidenciaSalida1.getId());
        assertThat(evidenciaSalida1).isEqualTo(evidenciaSalida2);

        evidenciaSalida2 = getEvidenciaSalidaSample2();
        assertThat(evidenciaSalida1).isNotEqualTo(evidenciaSalida2);
    }

    @Test
    void cuentaTest() {
        EvidenciaSalida evidenciaSalida = getEvidenciaSalidaRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        evidenciaSalida.setCuenta(cuentaBack);
        assertThat(evidenciaSalida.getCuenta()).isEqualTo(cuentaBack);

        evidenciaSalida.cuenta(null);
        assertThat(evidenciaSalida.getCuenta()).isNull();
    }

    @Test
    void sitioSubidaTest() {
        EvidenciaSalida evidenciaSalida = getEvidenciaSalidaRandomSampleGenerator();
        Sitio sitioBack = getSitioRandomSampleGenerator();

        evidenciaSalida.setSitioSubida(sitioBack);
        assertThat(evidenciaSalida.getSitioSubida()).isEqualTo(sitioBack);

        evidenciaSalida.sitioSubida(null);
        assertThat(evidenciaSalida.getSitioSubida()).isNull();
    }

    @Test
    void salidaTest() {
        EvidenciaSalida evidenciaSalida = getEvidenciaSalidaRandomSampleGenerator();
        Salida salidaBack = getSalidaRandomSampleGenerator();

        evidenciaSalida.setSalida(salidaBack);
        assertThat(evidenciaSalida.getSalida()).isEqualTo(salidaBack);
        assertThat(salidaBack.getEvidenciaSalida()).isEqualTo(evidenciaSalida);

        evidenciaSalida.salida(null);
        assertThat(evidenciaSalida.getSalida()).isNull();
        assertThat(salidaBack.getEvidenciaSalida()).isNull();
    }
}
