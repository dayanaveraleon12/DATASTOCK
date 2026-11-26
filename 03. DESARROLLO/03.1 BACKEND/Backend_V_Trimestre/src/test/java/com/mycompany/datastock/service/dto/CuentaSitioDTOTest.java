package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuentaSitioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaSitioDTO.class);
        CuentaSitioDTO cuentaSitioDTO1 = new CuentaSitioDTO();
        cuentaSitioDTO1.setId(1L);
        CuentaSitioDTO cuentaSitioDTO2 = new CuentaSitioDTO();
        assertThat(cuentaSitioDTO1).isNotEqualTo(cuentaSitioDTO2);
        cuentaSitioDTO2.setId(cuentaSitioDTO1.getId());
        assertThat(cuentaSitioDTO1).isEqualTo(cuentaSitioDTO2);
        cuentaSitioDTO2.setId(2L);
        assertThat(cuentaSitioDTO1).isNotEqualTo(cuentaSitioDTO2);
        cuentaSitioDTO1.setId(null);
        assertThat(cuentaSitioDTO1).isNotEqualTo(cuentaSitioDTO2);
    }
}
