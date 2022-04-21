import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITime } from 'app/shared/model/time.model';
import { getEntities as getTimes } from 'app/entities/time/time.reducer';
import { IJogador } from 'app/shared/model/jogador.model';
import { Posicao } from 'app/shared/model/enumerations/posicao.model';
import { getEntity, updateEntity, createEntity, reset } from './jogador.reducer';

export const JogadorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const times = useAppSelector(state => state.time.entities);
  const jogadorEntity = useAppSelector(state => state.jogador.entity);
  const loading = useAppSelector(state => state.jogador.loading);
  const updating = useAppSelector(state => state.jogador.updating);
  const updateSuccess = useAppSelector(state => state.jogador.updateSuccess);
  const posicaoValues = Object.keys(Posicao);
  const handleClose = () => {
    props.history.push('/jogador');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTimes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...jogadorEntity,
      ...values,
      time: times.find(it => it.id.toString() === values.time.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          posicao: 'Goleiro',
          ...jogadorEntity,
          time: jogadorEntity?.time?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="apiFutebolApp.jogador.home.createOrEditLabel" data-cy="JogadorCreateUpdateHeading">
            Create or edit a Jogador
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="jogador-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nome" id="jogador-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedBlobField label="Foto" id="jogador-foto" name="foto" data-cy="foto" isImage accept="image/*" />
              <ValidatedField label="Idade" id="jogador-idade" name="idade" data-cy="idade" type="text" />
              <ValidatedField label="Posicao" id="jogador-posicao" name="posicao" data-cy="posicao" type="select">
                {posicaoValues.map(posicao => (
                  <option value={posicao} key={posicao}>
                    {posicao}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Camisa" id="jogador-camisa" name="camisa" data-cy="camisa" type="text" />
              <ValidatedField label="Numerode Gols" id="jogador-numerodeGols" name="numerodeGols" data-cy="numerodeGols" type="text" />
              <ValidatedField id="jogador-time" name="time" data-cy="time" label="Time" type="select">
                <option value="" key="0" />
                {times
                  ? times.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/jogador" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JogadorUpdate;
