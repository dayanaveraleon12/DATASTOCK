package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistorialErrorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialErrorDTO.class);
        HistorialErrorDTO historialErrorDTO1 = new HistorialErrorDTO();
        historialErrorDTO1.setId(1L);
        HistorialErrorDTO historialErrorDTO2 = new HistorialErrorDTO();
        assertThat(historialErrorDTO1).isNotEqualTo(historialErrorDTO2);
        historialErrorDTO2.setId(historialErrorDTO1.getId());
        assertThat(historialErrorDTO1).isEqualTo(historialErrorDTO2);
        historialErrorDTO2.setId(2L);
        assertThat(historialErrorDTO1).isNotEqualTo(historialErrorDTO2);
        historialErrorDTO1.setId(null);
        assertThat(historialErrorDTO1).isNotEqualTo(historialErrorDTO2);
    }
}
