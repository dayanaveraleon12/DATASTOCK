package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.CuentaSitio;
import com.mycompany.datastock.repository.CuentaSitioRepository;
import com.mycompany.datastock.service.CuentaSitioService;
import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import com.mycompany.datastock.service.mapper.CuentaSitioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.CuentaSitio}.
 */
@Service
@Transactional
public class CuentaSitioServiceImpl implements CuentaSitioService {

    private static final Logger LOG = LoggerFactory.getLogger(CuentaSitioServiceImpl.class);

    private final CuentaSitioRepository cuentaSitioRepository;

    private final CuentaSitioMapper cuentaSitioMapper;

    public CuentaSitioServiceImpl(CuentaSitioRepository cuentaSitioRepository, CuentaSitioMapper cuentaSitioMapper) {
        this.cuentaSitioRepository = cuentaSitioRepository;
        this.cuentaSitioMapper = cuentaSitioMapper;
    }

    @Override
    public CuentaSitioDTO save(CuentaSitioDTO cuentaSitioDTO) {
        LOG.debug("Request to save CuentaSitio : {}", cuentaSitioDTO);
        CuentaSitio cuentaSitio = cuentaSitioMapper.toEntity(cuentaSitioDTO);
        cuentaSitio = cuentaSitioRepository.save(cuentaSitio);
        return cuentaSitioMapper.toDto(cuentaSitio);
    }

    @Override
    public CuentaSitioDTO update(CuentaSitioDTO cuentaSitioDTO) {
        LOG.debug("Request to update CuentaSitio : {}", cuentaSitioDTO);
        CuentaSitio cuentaSitio = cuentaSitioMapper.toEntity(cuentaSitioDTO);
        cuentaSitio = cuentaSitioRepository.save(cuentaSitio);
        return cuentaSitioMapper.toDto(cuentaSitio);
    }

    @Override
    public Optional<CuentaSitioDTO> partialUpdate(CuentaSitioDTO cuentaSitioDTO) {
        LOG.debug("Request to partially update CuentaSitio : {}", cuentaSitioDTO);

        return cuentaSitioRepository
            .findById(cuentaSitioDTO.getId())
            .map(existingCuentaSitio -> {
                cuentaSitioMapper.partialUpdate(existingCuentaSitio, cuentaSitioDTO);

                return existingCuentaSitio;
            })
            .map(cuentaSitioRepository::save)
            .map(cuentaSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuentaSitioDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CuentaSitios");
        return cuentaSitioRepository.findAll(pageable).map(cuentaSitioMapper::toDto);
    }

    public Page<CuentaSitioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return cuentaSitioRepository.findAllWithEagerRelationships(pageable).map(cuentaSitioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaSitioDTO> findOne(Long id) {
        LOG.debug("Request to get CuentaSitio : {}", id);
        return cuentaSitioRepository.findOneWithEagerRelationships(id).map(cuentaSitioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CuentaSitio : {}", id);
        cuentaSitioRepository.deleteById(id);
    }
}
