package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoProductoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoProductoDTO.class);
        EstadoProductoDTO estadoProductoDTO1 = new EstadoProductoDTO();
        estadoProductoDTO1.setId(1L);
        EstadoProductoDTO estadoProductoDTO2 = new EstadoProductoDTO();
        assertThat(estadoProductoDTO1).isNotEqualTo(estadoProductoDTO2);
        estadoProductoDTO2.setId(estadoProductoDTO1.getId());
        assertThat(estadoProductoDTO1).isEqualTo(estadoProductoDTO2);
        estadoProductoDTO2.setId(2L);
        assertThat(estadoProductoDTO1).isNotEqualTo(estadoProductoDTO2);
        estadoProductoDTO1.setId(null);
        assertThat(estadoProductoDTO1).isNotEqualTo(estadoProductoDTO2);
    }
}
