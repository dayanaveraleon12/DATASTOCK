package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.MotivoSalida;
import com.mycompany.datastock.repository.MotivoSalidaRepository;
import com.mycompany.datastock.service.MotivoSalidaService;
import com.mycompany.datastock.service.dto.MotivoSalidaDTO;
import com.mycompany.datastock.service.mapper.MotivoSalidaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.MotivoSalida}.
 */
@Service
@Transactional
public class MotivoSalidaServiceImpl implements MotivoSalidaService {

    private static final Logger LOG = LoggerFactory.getLogger(MotivoSalidaServiceImpl.class);

    private final MotivoSalidaRepository motivoSalidaRepository;

    private final MotivoSalidaMapper motivoSalidaMapper;

    public MotivoSalidaServiceImpl(MotivoSalidaRepository motivoSalidaRepository, MotivoSalidaMapper motivoSalidaMapper) {
        this.motivoSalidaRepository = motivoSalidaRepository;
        this.motivoSalidaMapper = motivoSalidaMapper;
    }

    @Override
    public MotivoSalidaDTO save(MotivoSalidaDTO motivoSalidaDTO) {
        LOG.debug("Request to save MotivoSalida : {}", motivoSalidaDTO);
        MotivoSalida motivoSalida = motivoSalidaMapper.toEntity(motivoSalidaDTO);
        motivoSalida = motivoSalidaRepository.save(motivoSalida);
        return motivoSalidaMapper.toDto(motivoSalida);
    }

    @Override
    public MotivoSalidaDTO update(MotivoSalidaDTO motivoSalidaDTO) {
        LOG.debug("Request to update MotivoSalida : {}", motivoSalidaDTO);
        MotivoSalida motivoSalida = motivoSalidaMapper.toEntity(motivoSalidaDTO);
        motivoSalida = motivoSalidaRepository.save(motivoSalida);
        return motivoSalidaMapper.toDto(motivoSalida);
    }

    @Override
    public Optional<MotivoSalidaDTO> partialUpdate(MotivoSalidaDTO motivoSalidaDTO) {
        LOG.debug("Request to partially update MotivoSalida : {}", motivoSalidaDTO);

        return motivoSalidaRepository
            .findById(motivoSalidaDTO.getId())
            .map(existingMotivoSalida -> {
                motivoSalidaMapper.partialUpdate(existingMotivoSalida, motivoSalidaDTO);

                return existingMotivoSalida;
            })
            .map(motivoSalidaRepository::save)
            .map(motivoSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MotivoSalidaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MotivoSalidas");
        return motivoSalidaRepository.findAll(pageable).map(motivoSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MotivoSalidaDTO> findOne(Long id) {
        LOG.debug("Request to get MotivoSalida : {}", id);
        return motivoSalidaRepository.findById(id).map(motivoSalidaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MotivoSalida : {}", id);
        motivoSalidaRepository.deleteById(id);
    }
}
