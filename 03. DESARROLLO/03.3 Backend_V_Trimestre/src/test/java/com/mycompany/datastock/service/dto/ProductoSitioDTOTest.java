package com.mycompany.datastock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.datastock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoSitioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoSitioDTO.class);
        ProductoSitioDTO productoSitioDTO1 = new ProductoSitioDTO();
        productoSitioDTO1.setId(1L);
        ProductoSitioDTO productoSitioDTO2 = new ProductoSitioDTO();
        assertThat(productoSitioDTO1).isNotEqualTo(productoSitioDTO2);
        productoSitioDTO2.setId(productoSitioDTO1.getId());
        assertThat(productoSitioDTO1).isEqualTo(productoSitioDTO2);
        productoSitioDTO2.setId(2L);
        assertThat(productoSitioDTO1).isNotEqualTo(productoSitioDTO2);
        productoSitioDTO1.setId(null);
        assertThat(productoSitioDTO1).isNotEqualTo(productoSitioDTO2);
    }
}
