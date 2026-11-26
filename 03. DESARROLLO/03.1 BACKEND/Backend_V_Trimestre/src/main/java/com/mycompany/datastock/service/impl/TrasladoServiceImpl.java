package com.mycompany.datastock.service.impl;

import com.mycompany.datastock.domain.Traslado;
import com.mycompany.datastock.repository.TrasladoRepository;
import com.mycompany.datastock.service.TrasladoService;
import com.mycompany.datastock.service.dto.TrasladoDTO;
import com.mycompany.datastock.service.mapper.TrasladoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.datastock.domain.Traslado}.
 */
@Service
@Transactional
public class TrasladoServiceImpl implements TrasladoService {

    private static final Logger LOG = LoggerFactory.getLogger(TrasladoServiceImpl.class);

    private final TrasladoRepository trasladoRepository;

    private final TrasladoMapper trasladoMapper;

    public TrasladoServiceImpl(TrasladoRepository trasladoRepository, TrasladoMapper trasladoMapper) {
        this.trasladoRepository = trasladoRepository;
        this.trasladoMapper = trasladoMapper;
    }

    @Override
    public TrasladoDTO save(TrasladoDTO trasladoDTO) {
        LOG.debug("Request to save Traslado : {}", trasladoDTO);
        Traslado traslado = trasladoMapper.toEntity(trasladoDTO);
        traslado = trasladoRepository.save(traslado);
        return trasladoMapper.toDto(traslado);
    }

    @Override
    public TrasladoDTO update(TrasladoDTO trasladoDTO) {
        LOG.debug("Request to update Traslado : {}", trasladoDTO);
        Traslado traslado = trasladoMapper.toEntity(trasladoDTO);
        traslado = trasladoRepository.save(traslado);
        return trasladoMapper.toDto(traslado);
    }

    @Override
    public Optional<TrasladoDTO> partialUpdate(TrasladoDTO trasladoDTO) {
        LOG.debug("Request to partially update Traslado : {}", trasladoDTO);

        return trasladoRepository
            .findById(trasladoDTO.getId())
            .map(existingTraslado -> {
                trasladoMapper.partialUpdate(existingTraslado, trasladoDTO);

                return existingTraslado;
            })
            .map(trasladoRepository::save)
            .map(trasladoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrasladoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Traslados");
        return trasladoRepository.findAll(pageable).map(trasladoMapper::toDto);
    }

    public Page<TrasladoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return trasladoRepository.findAllWithEagerRelationships(pageable).map(trasladoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrasladoDTO> findOne(Long id) {
        LOG.debug("Request to get Traslado : {}", id);
        return trasladoRepository.findOneWithEagerRelationships(id).map(trasladoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Traslado : {}", id);
        trasladoRepository.deleteById(id);
    }
}
