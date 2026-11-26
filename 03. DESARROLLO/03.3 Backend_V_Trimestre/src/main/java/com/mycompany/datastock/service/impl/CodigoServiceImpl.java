package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Codigo;
import com.mycompany.datastock.repository.CodigoRepository;
import com.mycompany.datastock.service.CodigoService;
import com.mycompany.datastock.service.dto.CodigoDTO;
import com.mycompany.datastock.service.mapper.CodigoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Codigo}.
 */
@Service
@Transactional
public class CodigoServiceImpl implements CodigoService {

    private static final Logger LOG = LoggerFactory.getLogger(CodigoServiceImpl.class);

    private final CodigoRepository codigoRepository;

    private final CodigoMapper codigoMapper;

    public CodigoServiceImpl(CodigoRepository codigoRepository, CodigoMapper codigoMapper) {
        this.codigoRepository = codigoRepository;
        this.codigoMapper = codigoMapper;
    }

    @Override
    public CodigoDTO save(CodigoDTO codigoDTO) {
        LOG.debug("Request to save Codigo : {}", codigoDTO);
        Codigo codigo = codigoMapper.toEntity(codigoDTO);
        codigo = codigoRepository.save(codigo);
        return codigoMapper.toDto(codigo);
    }

    @Override
    public CodigoDTO update(CodigoDTO codigoDTO) {
        LOG.debug("Request to update Codigo : {}", codigoDTO);
        Codigo codigo = codigoMapper.toEntity(codigoDTO);
        codigo = codigoRepository.save(codigo);
        return codigoMapper.toDto(codigo);
    }

    @Override
    public Optional<CodigoDTO> partialUpdate(CodigoDTO codigoDTO) {
        LOG.debug("Request to partially update Codigo : {}", codigoDTO);

        return codigoRepository
            .findById(codigoDTO.getId())
            .map(existingCodigo -> {
                codigoMapper.partialUpdate(existingCodigo, codigoDTO);

                return existingCodigo;
            })
            .map(codigoRepository::save)
            .map(codigoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodigoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Codigos");
        return codigoRepository.findAll(pageable).map(codigoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CodigoDTO> findOne(Long id) {
        LOG.debug("Request to get Codigo : {}", id);
        return codigoRepository.findById(id).map(codigoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Codigo : {}", id);
        codigoRepository.deleteById(id);
    }
}
