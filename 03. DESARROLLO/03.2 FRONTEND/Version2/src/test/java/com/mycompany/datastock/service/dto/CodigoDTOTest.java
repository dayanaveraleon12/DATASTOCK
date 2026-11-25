package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodigoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoDTO.class);
        CodigoDTO codigoDTO1 = new CodigoDTO();
        codigoDTO1.setId(1L);
        CodigoDTO codigoDTO2 = new CodigoDTO();
        assertThat(codigoDTO1).isNotEqualTo(codigoDTO2);
        codigoDTO2.setId(codigoDTO1.getId());
        assertThat(codigoDTO1).isEqualTo(codigoDTO2);
        codigoDTO2.setId(2L);
        assertThat(codigoDTO1).isNotEqualTo(codigoDTO2);
        codigoDTO1.setId(null);
        assertThat(codigoDTO1).isNotEqualTo(codigoDTO2);
    }
}
