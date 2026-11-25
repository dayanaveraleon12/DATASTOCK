package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Remision;
import com.mycompany.datastock.repository.RemisionRepository;
import com.mycompany.datastock.service.RemisionService;
import com.mycompany.datastock.service.dto.RemisionDTO;
import com.mycompany.datastock.service.mapper.RemisionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Remision}.
 */
@Service
@Transactional
public class RemisionServiceImpl implements RemisionService {

    private static final Logger LOG = LoggerFactory.getLogger(RemisionServiceImpl.class);

    private final RemisionRepository remisionRepository;

    private final RemisionMapper remisionMapper;

    public RemisionServiceImpl(RemisionRepository remisionRepository, RemisionMapper remisionMapper) {
        this.remisionRepository = remisionRepository;
        this.remisionMapper = remisionMapper;
    }

    @Override
    public RemisionDTO save(RemisionDTO remisionDTO) {
        LOG.debug("Request to save Remision : {}", remisionDTO);
        Remision remision = remisionMapper.toEntity(remisionDTO);
        remision = remisionRepository.save(remision);
        return remisionMapper.toDto(remision);
    }

    @Override
    public RemisionDTO update(RemisionDTO remisionDTO) {
        LOG.debug("Request to update Remision : {}", remisionDTO);
        Remision remision = remisionMapper.toEntity(remisionDTO);
        remision = remisionRepository.save(remision);
        return remisionMapper.toDto(remision);
    }

    @Override
    public Optional<RemisionDTO> partialUpdate(RemisionDTO remisionDTO) {
        LOG.debug("Request to partially update Remision : {}", remisionDTO);

        return remisionRepository
            .findById(remisionDTO.getId())
            .map(existingRemision -> {
                remisionMapper.partialUpdate(existingRemision, remisionDTO);

                return existingRemision;
            })
            .map(remisionRepository::save)
            .map(remisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RemisionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Remisions");
        return remisionRepository.findAll(pageable).map(remisionMapper::toDto);
    }

    public Page<RemisionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return remisionRepository.findAllWithEagerRelationships(pageable).map(remisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RemisionDTO> findOne(Long id) {
        LOG.debug("Request to get Remision : {}", id);
        return remisionRepository.findOneWithEagerRelationships(id).map(remisionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Remision : {}", id);
        remisionRepository.deleteById(id);
    }
}
