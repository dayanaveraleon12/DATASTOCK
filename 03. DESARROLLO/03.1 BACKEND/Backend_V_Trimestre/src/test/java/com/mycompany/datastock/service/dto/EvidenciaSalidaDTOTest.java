package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvidenciaSalidaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvidenciaSalidaDTO.class);
        EvidenciaSalidaDTO evidenciaSalidaDTO1 = new EvidenciaSalidaDTO();
        evidenciaSalidaDTO1.setId(1L);
        EvidenciaSalidaDTO evidenciaSalidaDTO2 = new EvidenciaSalidaDTO();
        assertThat(evidenciaSalidaDTO1).isNotEqualTo(evidenciaSalidaDTO2);
        evidenciaSalidaDTO2.setId(evidenciaSalidaDTO1.getId());
        assertThat(evidenciaSalidaDTO1).isEqualTo(evidenciaSalidaDTO2);
        evidenciaSalidaDTO2.setId(2L);
        assertThat(evidenciaSalidaDTO1).isNotEqualTo(evidenciaSalidaDTO2);
        evidenciaSalidaDTO1.setId(null);
        assertThat(evidenciaSalidaDTO1).isNotEqualTo(evidenciaSalidaDTO2);
    }
}
