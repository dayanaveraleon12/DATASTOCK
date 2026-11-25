package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.TrasladoProductoSitio;
import com.mycompany.datastock.repository.TrasladoProductoSitioRepository;
import com.mycompany.datastock.service.TrasladoProductoSitioService;
import com.mycompany.datastock.service.dto.TrasladoProductoSitioDTO;
import com.mycompany.datastock.service.mapper.TrasladoProductoSitioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.TrasladoProductoSitio}.
 */
@Service
@Transactional
public class TrasladoProductoSitioServiceImpl implements TrasladoProductoSitioService {

    private static final Logger LOG = LoggerFactory.getLogger(TrasladoProductoSitioServiceImpl.class);

    private final TrasladoProductoSitioRepository trasladoProductoSitioRepository;

    private final TrasladoProductoSitioMapper trasladoProductoSitioMapper;

    public TrasladoProductoSitioServiceImpl(
        TrasladoProductoSitioRepository trasladoProductoSitioRepository,
        TrasladoProductoSitioMapper trasladoProductoSitioMapper
    ) {
        this.trasladoProductoSitioRepository = trasladoProductoSitioRepository;
        this.trasladoProductoSitioMapper = trasladoProductoSitioMapper;
    }

    @Override
    public TrasladoProductoSitioDTO save(TrasladoProductoSitioDTO trasladoProductoSitioDTO) {
        LOG.debug("Request to save TrasladoProductoSitio : {}", trasladoProductoSitioDTO);
        TrasladoProductoSitio trasladoProductoSitio = trasladoProductoSitioMapper.toEntity(trasladoProductoSitioDTO);
        trasladoProductoSitio = trasladoProductoSitioRepository.save(trasladoProductoSitio);
        return trasladoProductoSitioMapper.toDto(trasladoProductoSitio);
    }

    @Override
    public TrasladoProductoSitioDTO update(TrasladoProductoSitioDTO trasladoProductoSitioDTO) {
        LOG.debug("Request to update TrasladoProductoSitio : {}", trasladoProductoSitioDTO);
        TrasladoProductoSitio trasladoProductoSitio = trasladoProductoSitioMapper.toEntity(trasladoProductoSitioDTO);
        trasladoProductoSitio = trasladoProductoSitioRepository.save(trasladoProductoSitio);
        return trasladoProductoSitioMapper.toDto(trasladoProductoSitio);
    }

    @Override
    public Optional<TrasladoProductoSitioDTO> partialUpdate(TrasladoProductoSitioDTO trasladoProductoSitioDTO) {
        LOG.debug("Request to partially update TrasladoProductoSitio : {}", trasladoProductoSitioDTO);

        return trasladoProductoSitioRepository
            .findById(trasladoProductoSitioDTO.getId())
            .map(existingTrasladoProductoSitio -> {
                trasladoProductoSitioMapper.partialUpdate(existingTrasladoProductoSitio, trasladoProductoSitioDTO);

                return existingTrasladoProductoSitio;
            })
            .map(trasladoProductoSitioRepository::save)
            .map(trasladoProductoSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrasladoProductoSitioDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TrasladoProductoSitios");
        return trasladoProductoSitioRepository.findAll(pageable).map(trasladoProductoSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrasladoProductoSitioDTO> findOne(Long id) {
        LOG.debug("Request to get TrasladoProductoSitio : {}", id);
        return trasladoProductoSitioRepository.findById(id).map(trasladoProductoSitioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TrasladoProductoSitio : {}", id);
        trasladoProductoSitioRepository.deleteById(id);
    }
}
