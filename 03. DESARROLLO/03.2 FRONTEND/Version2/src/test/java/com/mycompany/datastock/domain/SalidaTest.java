package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;
import static com.mycompany.datastock.domain.EvidenciaSalidaTestSamples.*;
import static com.mycompany.datastock.domain.MotivoSalidaTestSamples.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SalidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salida.class);
        Salida salida1 = getSalidaSample1();
        Salida salida2 = new Salida();
        assertThat(salida1).isNotEqualTo(salida2);

        salida2.setId(salida1.getId());
        assertThat(salida1).isEqualTo(salida2);

        salida2 = getSalidaSample2();
        assertThat(salida1).isNotEqualTo(salida2);
    }

    @Test
    void cuentaSitioTest() {
        Salida salida = getSalidaRandomSampleGenerator();
        CuentaSitio cuentaSitioBack = getCuentaSitioRandomSampleGenerator();

        salida.setCuentaSitio(cuentaSitioBack);
        assertThat(salida.getCuentaSitio()).isEqualTo(cuentaSitioBack);

        salida.cuentaSitio(null);
        assertThat(salida.getCuentaSitio()).isNull();
    }

    @Test
    void motivoSalidaTest() {
        Salida salida = getSalidaRandomSampleGenerator();
        MotivoSalida motivoSalidaBack = getMotivoSalidaRandomSampleGenerator();

        salida.setMotivoSalida(motivoSalidaBack);
        assertThat(salida.getMotivoSalida()).isEqualTo(motivoSalidaBack);

        salida.motivoSalida(null);
        assertThat(salida.getMotivoSalida()).isNull();
    }

    @Test
    void evidenciaSalidaTest() {
        Salida salida = getSalidaRandomSampleGenerator();
        EvidenciaSalida evidenciaSalidaBack = getEvidenciaSalidaRandomSampleGenerator();

        salida.setEvidenciaSalida(evidenciaSalidaBack);
        assertThat(salida.getEvidenciaSalida()).isEqualTo(evidenciaSalidaBack);

        salida.evidenciaSalida(null);
        assertThat(salida.getEvidenciaSalida()).isNull();
    }

    @Test
    void productoSitiosTest() {
        Salida salida = getSalidaRandomSampleGenerator();
        ProductoSitio productoSitioBack = getProductoSitioRandomSampleGenerator();

        salida.addProductoSitios(productoSitioBack);
        assertThat(salida.getProductoSitioses()).containsOnly(productoSitioBack);

        salida.removeProductoSitios(productoSitioBack);
        assertThat(salida.getProductoSitioses()).doesNotContain(productoSitioBack);

        salida.productoSitioses(new HashSet<>(Set.of(productoSitioBack)));
        assertThat(salida.getProductoSitioses()).containsOnly(productoSitioBack);

        salida.setProductoSitioses(new HashSet<>());
        assertThat(salida.getProductoSitioses()).doesNotContain(productoSitioBack);
    }
}
