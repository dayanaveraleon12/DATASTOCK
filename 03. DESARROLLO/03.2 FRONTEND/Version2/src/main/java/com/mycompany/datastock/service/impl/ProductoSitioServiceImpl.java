package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.ProductoSitio;
import com.mycompany.datastock.repository.ProductoSitioRepository;
import com.mycompany.datastock.service.ProductoSitioService;
import com.mycompany.datastock.service.dto.ProductoSitioDTO;
import com.mycompany.datastock.service.mapper.ProductoSitioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.ProductoSitio}.
 */
@Service
@Transactional
public class ProductoSitioServiceImpl implements ProductoSitioService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoSitioServiceImpl.class);

    private final ProductoSitioRepository productoSitioRepository;

    private final ProductoSitioMapper productoSitioMapper;

    public ProductoSitioServiceImpl(ProductoSitioRepository productoSitioRepository, ProductoSitioMapper productoSitioMapper) {
        this.productoSitioRepository = productoSitioRepository;
        this.productoSitioMapper = productoSitioMapper;
    }

    @Override
    public ProductoSitioDTO save(ProductoSitioDTO productoSitioDTO) {
        LOG.debug("Request to save ProductoSitio : {}", productoSitioDTO);
        ProductoSitio productoSitio = productoSitioMapper.toEntity(productoSitioDTO);
        productoSitio = productoSitioRepository.save(productoSitio);
        return productoSitioMapper.toDto(productoSitio);
    }

    @Override
    public ProductoSitioDTO update(ProductoSitioDTO productoSitioDTO) {
        LOG.debug("Request to update ProductoSitio : {}", productoSitioDTO);
        ProductoSitio productoSitio = productoSitioMapper.toEntity(productoSitioDTO);
        productoSitio = productoSitioRepository.save(productoSitio);
        return productoSitioMapper.toDto(productoSitio);
    }

    @Override
    public Optional<ProductoSitioDTO> partialUpdate(ProductoSitioDTO productoSitioDTO) {
        LOG.debug("Request to partially update ProductoSitio : {}", productoSitioDTO);

        return productoSitioRepository
            .findById(productoSitioDTO.getId())
            .map(existingProductoSitio -> {
                productoSitioMapper.partialUpdate(existingProductoSitio, productoSitioDTO);

                return existingProductoSitio;
            })
            .map(productoSitioRepository::save)
            .map(productoSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoSitioDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ProductoSitios");
        return productoSitioRepository.findAll(pageable).map(productoSitioMapper::toDto);
    }

    public Page<ProductoSitioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productoSitioRepository.findAllWithEagerRelationships(pageable).map(productoSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoSitioDTO> findOne(Long id) {
        LOG.debug("Request to get ProductoSitio : {}", id);
        return productoSitioRepository.findOneWithEagerRelationships(id).map(productoSitioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ProductoSitio : {}", id);
        productoSitioRepository.deleteById(id);
    }
}
