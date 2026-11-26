package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotivoSalidaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoSalidaDTO.class);
        MotivoSalidaDTO motivoSalidaDTO1 = new MotivoSalidaDTO();
        motivoSalidaDTO1.setId(1L);
        MotivoSalidaDTO motivoSalidaDTO2 = new MotivoSalidaDTO();
        assertThat(motivoSalidaDTO1).isNotEqualTo(motivoSalidaDTO2);
        motivoSalidaDTO2.setId(motivoSalidaDTO1.getId());
        assertThat(motivoSalidaDTO1).isEqualTo(motivoSalidaDTO2);
        motivoSalidaDTO2.setId(2L);
        assertThat(motivoSalidaDTO1).isNotEqualTo(motivoSalidaDTO2);
        motivoSalidaDTO1.setId(null);
        assertThat(motivoSalidaDTO1).isNotEqualTo(motivoSalidaDTO2);
    }
}
