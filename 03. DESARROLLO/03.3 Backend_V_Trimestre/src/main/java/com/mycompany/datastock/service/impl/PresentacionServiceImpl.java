package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Presentacion;
import com.mycompany.datastock.repository.PresentacionRepository;
import com.mycompany.datastock.service.PresentacionService;
import com.mycompany.datastock.service.dto.PresentacionDTO;
import com.mycompany.datastock.service.mapper.PresentacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Presentacion}.
 */
@Service
@Transactional
public class PresentacionServiceImpl implements PresentacionService {

    private static final Logger LOG = LoggerFactory.getLogger(PresentacionServiceImpl.class);

    private final PresentacionRepository presentacionRepository;

    private final PresentacionMapper presentacionMapper;

    public PresentacionServiceImpl(PresentacionRepository presentacionRepository, PresentacionMapper presentacionMapper) {
        this.presentacionRepository = presentacionRepository;
        this.presentacionMapper = presentacionMapper;
    }

    @Override
    public PresentacionDTO save(PresentacionDTO presentacionDTO) {
        LOG.debug("Request to save Presentacion : {}", presentacionDTO);
        Presentacion presentacion = presentacionMapper.toEntity(presentacionDTO);
        presentacion = presentacionRepository.save(presentacion);
        return presentacionMapper.toDto(presentacion);
    }

    @Override
    public PresentacionDTO update(PresentacionDTO presentacionDTO) {
        LOG.debug("Request to update Presentacion : {}", presentacionDTO);
        Presentacion presentacion = presentacionMapper.toEntity(presentacionDTO);
        presentacion = presentacionRepository.save(presentacion);
        return presentacionMapper.toDto(presentacion);
    }

    @Override
    public Optional<PresentacionDTO> partialUpdate(PresentacionDTO presentacionDTO) {
        LOG.debug("Request to partially update Presentacion : {}", presentacionDTO);

        return presentacionRepository
            .findById(presentacionDTO.getId())
            .map(existingPresentacion -> {
                presentacionMapper.partialUpdate(existingPresentacion, presentacionDTO);

                return existingPresentacion;
            })
            .map(presentacionRepository::save)
            .map(presentacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PresentacionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Presentacions");
        return presentacionRepository.findAll(pageable).map(presentacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PresentacionDTO> findOne(Long id) {
        LOG.debug("Request to get Presentacion : {}", id);
        return presentacionRepository.findById(id).map(presentacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Presentacion : {}", id);
        presentacionRepository.deleteById(id);
    }
}
