package com.mycompany.datastock.repository;

import com.mycompany.datastock.domain.Salida;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SalidaRepositoryWithBagRelationshipsImpl implements SalidaRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SALIDAS_PARAMETER = "salidas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Salida> fetchBagRelationships(Optional<Salida> salida) {
        return salida.map(this::fetchProductoSitioses);
    }

    @Override
    public Page<Salida> fetchBagRelationships(Page<Salida> salidas) {
        return new PageImpl<>(fetchBagRelationships(salidas.getContent()), salidas.getPageable(), salidas.getTotalElements());
    }

    @Override
    public List<Salida> fetchBagRelationships(List<Salida> salidas) {
        return Optional.of(salidas).map(this::fetchProductoSitioses).orElse(List.of());
    }

    Salida fetchProductoSitioses(Salida result) {
        return entityManager
            .createQuery("select salida from Salida salida left join fetch salida.productoSitioses where salida.id = :id", Salida.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Salida> fetchProductoSitioses(List<Salida> salidas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, salidas.size()).forEach(index -> order.put(salidas.get(index).getId(), index));
        List<Salida> result = entityManager
            .createQuery("select salida from Salida salida left join fetch salida.productoSitioses where salida in :salidas", Salida.class)
            .setParameter(SALIDAS_PARAMETER, salidas)
            .getResultList();
        result.sort((o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
