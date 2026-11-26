package com.mycompany.datastock.domain;

import static com.mycompany.datastock.domain.MotivoSalidaTestSamples.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MotivoSalidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoSalida.class);
        MotivoSalida motivoSalida1 = getMotivoSalidaSample1();
        MotivoSalida motivoSalida2 = new MotivoSalida();
        assertThat(motivoSalida1).isNotEqualTo(motivoSalida2);

        motivoSalida2.setId(motivoSalida1.getId());
        assertThat(motivoSalida1).isEqualTo(motivoSalida2);

        motivoSalida2 = getMotivoSalidaSample2();
        assertThat(motivoSalida1).isNotEqualTo(motivoSalida2);
    }

    @Test
    void salidasTest() {
        MotivoSalida motivoSalida = getMotivoSalidaRandomSampleGenerator();
        Salida salidaBack = getSalidaRandomSampleGenerator();

        motivoSalida.addSalidas(salidaBack);
        assertThat(motivoSalida.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getMotivoSalida()).isEqualTo(motivoSalida);

        motivoSalida.removeSalidas(salidaBack);
        assertThat(motivoSalida.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getMotivoSalida()).isNull();

        motivoSalida.salidases(new HashSet<>(Set.of(salidaBack)));
        assertThat(motivoSalida.getSalidases()).containsOnly(salidaBack);
        assertThat(salidaBack.getMotivoSalida()).isEqualTo(motivoSalida);

        motivoSalida.setSalidases(new HashSet<>());
        assertThat(motivoSalida.getSalidases()).doesNotContain(salidaBack);
        assertThat(salidaBack.getMotivoSalida()).isNull();
    }
}
