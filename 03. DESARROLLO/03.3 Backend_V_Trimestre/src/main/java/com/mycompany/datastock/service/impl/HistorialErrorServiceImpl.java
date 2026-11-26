package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.HistorialError;
import com.mycompany.datastock.repository.HistorialErrorRepository;
import com.mycompany.datastock.service.HistorialErrorService;
import com.mycompany.datastock.service.dto.HistorialErrorDTO;
import com.mycompany.datastock.service.mapper.HistorialErrorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.HistorialError}.
 */
@Service
@Transactional
public class HistorialErrorServiceImpl implements HistorialErrorService {

    private static final Logger LOG = LoggerFactory.getLogger(HistorialErrorServiceImpl.class);

    private final HistorialErrorRepository historialErrorRepository;

    private final HistorialErrorMapper historialErrorMapper;

    public HistorialErrorServiceImpl(HistorialErrorRepository historialErrorRepository, HistorialErrorMapper historialErrorMapper) {
        this.historialErrorRepository = historialErrorRepository;
        this.historialErrorMapper = historialErrorMapper;
    }

    @Override
    public HistorialErrorDTO save(HistorialErrorDTO historialErrorDTO) {
        LOG.debug("Request to save HistorialError : {}", historialErrorDTO);
        HistorialError historialError = historialErrorMapper.toEntity(historialErrorDTO);
        historialError = historialErrorRepository.save(historialError);
        return historialErrorMapper.toDto(historialError);
    }

    @Override
    public HistorialErrorDTO update(HistorialErrorDTO historialErrorDTO) {
        LOG.debug("Request to update HistorialError : {}", historialErrorDTO);
        HistorialError historialError = historialErrorMapper.toEntity(historialErrorDTO);
        historialError = historialErrorRepository.save(historialError);
        return historialErrorMapper.toDto(historialError);
    }

    @Override
    public Optional<HistorialErrorDTO> partialUpdate(HistorialErrorDTO historialErrorDTO) {
        LOG.debug("Request to partially update HistorialError : {}", historialErrorDTO);

        return historialErrorRepository
            .findById(historialErrorDTO.getId())
            .map(existingHistorialError -> {
                historialErrorMapper.partialUpdate(existingHistorialError, historialErrorDTO);

                return existingHistorialError;
            })
            .map(historialErrorRepository::save)
            .map(historialErrorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistorialErrorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HistorialErrors");
        return historialErrorRepository.findAll(pageable).map(historialErrorMapper::toDto);
    }

    public Page<HistorialErrorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return historialErrorRepository.findAllWithEagerRelationships(pageable).map(historialErrorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialErrorDTO> findOne(Long id) {
        LOG.debug("Request to get HistorialError : {}", id);
        return historialErrorRepository.findOneWithEagerRelationships(id).map(historialErrorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete HistorialError : {}", id);
        historialErrorRepository.deleteById(id);
    }
}
