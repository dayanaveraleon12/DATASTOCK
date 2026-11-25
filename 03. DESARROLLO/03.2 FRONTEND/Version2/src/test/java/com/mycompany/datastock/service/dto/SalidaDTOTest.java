package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalidaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalidaDTO.class);
        SalidaDTO salidaDTO1 = new SalidaDTO();
        salidaDTO1.setId(1L);
        SalidaDTO salidaDTO2 = new SalidaDTO();
        assertThat(salidaDTO1).isNotEqualTo(salidaDTO2);
        salidaDTO2.setId(salidaDTO1.getId());
        assertThat(salidaDTO1).isEqualTo(salidaDTO2);
        salidaDTO2.setId(2L);
        assertThat(salidaDTO1).isNotEqualTo(salidaDTO2);
        salidaDTO1.setId(null);
        assertThat(salidaDTO1).isNotEqualTo(salidaDTO2);
    }
}
