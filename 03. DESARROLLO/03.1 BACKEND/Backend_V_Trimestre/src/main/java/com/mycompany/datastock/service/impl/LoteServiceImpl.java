package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Lote;
import com.mycompany.datastock.repository.LoteRepository;
import com.mycompany.datastock.service.LoteService;
import com.mycompany.datastock.service.dto.LoteDTO;
import com.mycompany.datastock.service.mapper.LoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Lote}.
 */
@Service
@Transactional
public class LoteServiceImpl implements LoteService {

    private static final Logger LOG = LoggerFactory.getLogger(LoteServiceImpl.class);

    private final LoteRepository loteRepository;

    private final LoteMapper loteMapper;

    public LoteServiceImpl(LoteRepository loteRepository, LoteMapper loteMapper) {
        this.loteRepository = loteRepository;
        this.loteMapper = loteMapper;
    }

    @Override
    public LoteDTO save(LoteDTO loteDTO) {
        LOG.debug("Request to save Lote : {}", loteDTO);
        Lote lote = loteMapper.toEntity(loteDTO);
        lote = loteRepository.save(lote);
        return loteMapper.toDto(lote);
    }

    @Override
    public LoteDTO update(LoteDTO loteDTO) {
        LOG.debug("Request to update Lote : {}", loteDTO);
        Lote lote = loteMapper.toEntity(loteDTO);
        lote = loteRepository.save(lote);
        return loteMapper.toDto(lote);
    }

    @Override
    public Optional<LoteDTO> partialUpdate(LoteDTO loteDTO) {
        LOG.debug("Request to partially update Lote : {}", loteDTO);

        return loteRepository
            .findById(loteDTO.getId())
            .map(existingLote -> {
                loteMapper.partialUpdate(existingLote, loteDTO);

                return existingLote;
            })
            .map(loteRepository::save)
            .map(loteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Lotes");
        return loteRepository.findAll(pageable).map(loteMapper::toDto);
    }

    public Page<LoteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return loteRepository.findAllWithEagerRelationships(pageable).map(loteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoteDTO> findOne(Long id) {
        LOG.debug("Request to get Lote : {}", id);
        return loteRepository.findOneWithEagerRelationships(id).map(loteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Lote : {}", id);
        loteRepository.deleteById(id);
    }
}
