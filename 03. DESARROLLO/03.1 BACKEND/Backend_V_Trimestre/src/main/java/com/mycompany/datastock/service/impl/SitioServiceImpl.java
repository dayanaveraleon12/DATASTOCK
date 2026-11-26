package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Sitio;
import com.mycompany.datastock.repository.SitioRepository;
import com.mycompany.datastock.service.SitioService;
import com.mycompany.datastock.service.dto.SitioDTO;
import com.mycompany.datastock.service.mapper.SitioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Sitio}.
 */
@Service
@Transactional
public class SitioServiceImpl implements SitioService {

    private static final Logger LOG = LoggerFactory.getLogger(SitioServiceImpl.class);

    private final SitioRepository sitioRepository;

    private final SitioMapper sitioMapper;

    public SitioServiceImpl(SitioRepository sitioRepository, SitioMapper sitioMapper) {
        this.sitioRepository = sitioRepository;
        this.sitioMapper = sitioMapper;
    }

    @Override
    public SitioDTO save(SitioDTO sitioDTO) {
        LOG.debug("Request to save Sitio : {}", sitioDTO);
        Sitio sitio = sitioMapper.toEntity(sitioDTO);
        sitio = sitioRepository.save(sitio);
        return sitioMapper.toDto(sitio);
    }

    @Override
    public SitioDTO update(SitioDTO sitioDTO) {
        LOG.debug("Request to update Sitio : {}", sitioDTO);
        Sitio sitio = sitioMapper.toEntity(sitioDTO);
        sitio = sitioRepository.save(sitio);
        return sitioMapper.toDto(sitio);
    }

    @Override
    public Optional<SitioDTO> partialUpdate(SitioDTO sitioDTO) {
        LOG.debug("Request to partially update Sitio : {}", sitioDTO);

        return sitioRepository
            .findById(sitioDTO.getId())
            .map(existingSitio -> {
                sitioMapper.partialUpdate(existingSitio, sitioDTO);

                return existingSitio;
            })
            .map(sitioRepository::save)
            .map(sitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SitioDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sitios");
        return sitioRepository.findAll(pageable).map(sitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SitioDTO> findOne(Long id) {
        LOG.debug("Request to get Sitio : {}", id);
        return sitioRepository.findById(id).map(sitioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Sitio : {}", id);
        sitioRepository.deleteById(id);
    }
}
