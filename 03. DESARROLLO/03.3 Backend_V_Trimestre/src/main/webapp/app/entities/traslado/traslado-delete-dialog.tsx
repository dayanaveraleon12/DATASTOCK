import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { useLocation, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './traslado.reducer';

export const TrasladoDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id!));
    setLoadModal(true);
  }, []);

  const trasladoEntity = useAppSelector(state => state.traslado.entity);
  const updateSuccess = useAppSelector(state => state.traslado.updateSuccess);

  const handleClose = () => {
    navigate(`/traslado${pageLocation.search}`);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(trasladoEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="trasladoDeleteDialogHeading" closeButton>
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="datastockApp.traslado.delete.question">¿Seguro que quiere eliminar Traslado {trasladoEntity.id}?</ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="project-confirm-delete-traslado" data-cy="entityConfirmDeleteButton" variant="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Eliminar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default TrasladoDeleteDialog;
