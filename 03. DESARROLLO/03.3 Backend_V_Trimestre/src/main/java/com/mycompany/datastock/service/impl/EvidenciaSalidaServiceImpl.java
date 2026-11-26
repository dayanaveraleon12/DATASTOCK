package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.EvidenciaSalida;
import com.mycompany.datastock.repository.EvidenciaSalidaRepository;
import com.mycompany.datastock.service.EvidenciaSalidaService;
import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
import com.mycompany.datastock.service.mapper.EvidenciaSalidaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.EvidenciaSalida}.
 */
@Service
@Transactional
public class EvidenciaSalidaServiceImpl implements EvidenciaSalidaService {

    private static final Logger LOG = LoggerFactory.getLogger(EvidenciaSalidaServiceImpl.class);

    private final EvidenciaSalidaRepository evidenciaSalidaRepository;

    private final EvidenciaSalidaMapper evidenciaSalidaMapper;

    public EvidenciaSalidaServiceImpl(EvidenciaSalidaRepository evidenciaSalidaRepository, EvidenciaSalidaMapper evidenciaSalidaMapper) {
        this.evidenciaSalidaRepository = evidenciaSalidaRepository;
        this.evidenciaSalidaMapper = evidenciaSalidaMapper;
    }

    @Override
    public EvidenciaSalidaDTO save(EvidenciaSalidaDTO evidenciaSalidaDTO) {
        LOG.debug("Request to save EvidenciaSalida : {}", evidenciaSalidaDTO);
        EvidenciaSalida evidenciaSalida = evidenciaSalidaMapper.toEntity(evidenciaSalidaDTO);
        evidenciaSalida = evidenciaSalidaRepository.save(evidenciaSalida);
        return evidenciaSalidaMapper.toDto(evidenciaSalida);
    }

    @Override
    public EvidenciaSalidaDTO update(EvidenciaSalidaDTO evidenciaSalidaDTO) {
        LOG.debug("Request to update EvidenciaSalida : {}", evidenciaSalidaDTO);
        EvidenciaSalida evidenciaSalida = evidenciaSalidaMapper.toEntity(evidenciaSalidaDTO);
        evidenciaSalida = evidenciaSalidaRepository.save(evidenciaSalida);
        return evidenciaSalidaMapper.toDto(evidenciaSalida);
    }

    @Override
    public Optional<EvidenciaSalidaDTO> partialUpdate(EvidenciaSalidaDTO evidenciaSalidaDTO) {
        LOG.debug("Request to partially update EvidenciaSalida : {}", evidenciaSalidaDTO);

        return evidenciaSalidaRepository
            .findById(evidenciaSalidaDTO.getId())
            .map(existingEvidenciaSalida -> {
                evidenciaSalidaMapper.partialUpdate(existingEvidenciaSalida, evidenciaSalidaDTO);

                return existingEvidenciaSalida;
            })
            .map(evidenciaSalidaRepository::save)
            .map(evidenciaSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvidenciaSalidaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EvidenciaSalidas");
        return evidenciaSalidaRepository.findAll(pageable).map(evidenciaSalidaMapper::toDto);
    }

    public Page<EvidenciaSalidaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return evidenciaSalidaRepository.findAllWithEagerRelationships(pageable).map(evidenciaSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvidenciaSalidaDTO> findOne(Long id) {
        LOG.debug("Request to get EvidenciaSalida : {}", id);
        return evidenciaSalidaRepository.findOneWithEagerRelationships(id).map(evidenciaSalidaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete EvidenciaSalida : {}", id);
        evidenciaSalidaRepository.deleteById(id);
    }
}
