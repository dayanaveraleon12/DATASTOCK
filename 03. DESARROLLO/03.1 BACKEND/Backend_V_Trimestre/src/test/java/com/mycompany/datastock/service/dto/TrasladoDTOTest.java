package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrasladoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrasladoDTO.class);
        TrasladoDTO trasladoDTO1 = new TrasladoDTO();
        trasladoDTO1.setId(1L);
        TrasladoDTO trasladoDTO2 = new TrasladoDTO();
        assertThat(trasladoDTO1).isNotEqualTo(trasladoDTO2);
        trasladoDTO2.setId(trasladoDTO1.getId());
        assertThat(trasladoDTO1).isEqualTo(trasladoDTO2);
        trasladoDTO2.setId(2L);
        assertThat(trasladoDTO1).isNotEqualTo(trasladoDTO2);
        trasladoDTO1.setId(null);
        assertThat(trasladoDTO1).isNotEqualTo(trasladoDTO2);
    }
}
