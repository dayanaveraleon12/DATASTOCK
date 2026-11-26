package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Salida;
import com.mycompany.datastock.repository.SalidaRepository;
import com.mycompany.datastock.service.SalidaService;
import com.mycompany.datastock.service.dto.SalidaDTO;
import com.mycompany.datastock.service.mapper.SalidaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Salida}.
 */
@Service
@Transactional
public class SalidaServiceImpl implements SalidaService {

    private static final Logger LOG = LoggerFactory.getLogger(SalidaServiceImpl.class);

    private final SalidaRepository salidaRepository;

    private final SalidaMapper salidaMapper;

    public SalidaServiceImpl(SalidaRepository salidaRepository, SalidaMapper salidaMapper) {
        this.salidaRepository = salidaRepository;
        this.salidaMapper = salidaMapper;
    }

    @Override
    public SalidaDTO save(SalidaDTO salidaDTO) {
        LOG.debug("Request to save Salida : {}", salidaDTO);
        Salida salida = salidaMapper.toEntity(salidaDTO);
        salida = salidaRepository.save(salida);
        return salidaMapper.toDto(salida);
    }

    @Override
    public SalidaDTO update(SalidaDTO salidaDTO) {
        LOG.debug("Request to update Salida : {}", salidaDTO);
        Salida salida = salidaMapper.toEntity(salidaDTO);
        salida = salidaRepository.save(salida);
        return salidaMapper.toDto(salida);
    }

    @Override
    public Optional<SalidaDTO> partialUpdate(SalidaDTO salidaDTO) {
        LOG.debug("Request to partially update Salida : {}", salidaDTO);

        return salidaRepository
            .findById(salidaDTO.getId())
            .map(existingSalida -> {
                salidaMapper.partialUpdate(existingSalida, salidaDTO);

                return existingSalida;
            })
            .map(salidaRepository::save)
            .map(salidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalidaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Salidas");
        return salidaRepository.findAll(pageable).map(salidaMapper::toDto);
    }

    public Page<SalidaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return salidaRepository.findAllWithEagerRelationships(pageable).map(salidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalidaDTO> findOne(Long id) {
        LOG.debug("Request to get Salida : {}", id);
        return salidaRepository.findOneWithEagerRelationships(id).map(salidaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Salida : {}", id);
        salidaRepository.deleteById(id);
    }
}
