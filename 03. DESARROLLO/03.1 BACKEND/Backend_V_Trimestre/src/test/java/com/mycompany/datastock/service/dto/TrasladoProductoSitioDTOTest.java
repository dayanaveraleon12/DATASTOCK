package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrasladoProductoSitioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrasladoProductoSitioDTO.class);
        TrasladoProductoSitioDTO trasladoProductoSitioDTO1 = new TrasladoProductoSitioDTO();
        trasladoProductoSitioDTO1.setId(1L);
        TrasladoProductoSitioDTO trasladoProductoSitioDTO2 = new TrasladoProductoSitioDTO();
        assertThat(trasladoProductoSitioDTO1).isNotEqualTo(trasladoProductoSitioDTO2);
        trasladoProductoSitioDTO2.setId(trasladoProductoSitioDTO1.getId());
        assertThat(trasladoProductoSitioDTO1).isEqualTo(trasladoProductoSitioDTO2);
        trasladoProductoSitioDTO2.setId(2L);
        assertThat(trasladoProductoSitioDTO1).isNotEqualTo(trasladoProductoSitioDTO2);
        trasladoProductoSitioDTO1.setId(null);
        assertThat(trasladoProductoSitioDTO1).isNotEqualTo(trasladoProductoSitioDTO2);
    }
}
