package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemisionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemisionDTO.class);
        RemisionDTO remisionDTO1 = new RemisionDTO();
        remisionDTO1.setId(1L);
        RemisionDTO remisionDTO2 = new RemisionDTO();
        assertThat(remisionDTO1).isNotEqualTo(remisionDTO2);
        remisionDTO2.setId(remisionDTO1.getId());
        assertThat(remisionDTO1).isEqualTo(remisionDTO2);
        remisionDTO2.setId(2L);
        assertThat(remisionDTO1).isNotEqualTo(remisionDTO2);
        remisionDTO1.setId(null);
        assertThat(remisionDTO1).isNotEqualTo(remisionDTO2);
    }
}
