package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.EstadoProducto;
import com.mycompany.datastock.repository.EstadoProductoRepository;
import com.mycompany.datastock.service.EstadoProductoService;
import com.mycompany.datastock.service.dto.EstadoProductoDTO;
import com.mycompany.datastock.service.mapper.EstadoProductoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.EstadoProducto}.
 */
@Service
@Transactional
public class EstadoProductoServiceImpl implements EstadoProductoService {

    private static final Logger LOG = LoggerFactory.getLogger(EstadoProductoServiceImpl.class);

    private final EstadoProductoRepository estadoProductoRepository;

    private final EstadoProductoMapper estadoProductoMapper;

    public EstadoProductoServiceImpl(EstadoProductoRepository estadoProductoRepository, EstadoProductoMapper estadoProductoMapper) {
        this.estadoProductoRepository = estadoProductoRepository;
        this.estadoProductoMapper = estadoProductoMapper;
    }

    @Override
    public EstadoProductoDTO save(EstadoProductoDTO estadoProductoDTO) {
        LOG.debug("Request to save EstadoProducto : {}", estadoProductoDTO);
        EstadoProducto estadoProducto = estadoProductoMapper.toEntity(estadoProductoDTO);
        estadoProducto = estadoProductoRepository.save(estadoProducto);
        return estadoProductoMapper.toDto(estadoProducto);
    }

    @Override
    public EstadoProductoDTO update(EstadoProductoDTO estadoProductoDTO) {
        LOG.debug("Request to update EstadoProducto : {}", estadoProductoDTO);
        EstadoProducto estadoProducto = estadoProductoMapper.toEntity(estadoProductoDTO);
        estadoProducto = estadoProductoRepository.save(estadoProducto);
        return estadoProductoMapper.toDto(estadoProducto);
    }

    @Override
    public Optional<EstadoProductoDTO> partialUpdate(EstadoProductoDTO estadoProductoDTO) {
        LOG.debug("Request to partially update EstadoProducto : {}", estadoProductoDTO);

        return estadoProductoRepository
            .findById(estadoProductoDTO.getId())
            .map(existingEstadoProducto -> {
                estadoProductoMapper.partialUpdate(existingEstadoProducto, estadoProductoDTO);

                return existingEstadoProducto;
            })
            .map(estadoProductoRepository::save)
            .map(estadoProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoProductoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EstadoProductos");
        return estadoProductoRepository.findAll(pageable).map(estadoProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoProductoDTO> findOne(Long id) {
        LOG.debug("Request to get EstadoProducto : {}", id);
        return estadoProductoRepository.findById(id).map(estadoProductoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete EstadoProducto : {}", id);
        estadoProductoRepository.deleteById(id);
    }
}
