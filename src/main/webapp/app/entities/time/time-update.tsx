import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITime } from 'app/shared/model/time.model';
import { EstadoOrigem } from 'app/shared/model/enumerations/estado-origem.model';
import { getEntity, updateEntity, createEntity, reset } from './time.reducer';

export const TimeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const timeEntity = useAppSelector(state => state.time.entity);
  const loading = useAppSelector(state => state.time.loading);
  const updating = useAppSelector(state => state.time.updating);
  const updateSuccess = useAppSelector(state => state.time.updateSuccess);
  const estadoOrigemValues = Object.keys(EstadoOrigem);
  const handleClose = () => {
    props.history.push('/time');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...timeEntity,
      ...values,
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
          estadoOrigem: 'RO',
          ...timeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="apiFutebolApp.time.home.createOrEditLabel" data-cy="TimeCreateUpdateHeading">
            Create or edit a Time
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="time-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedBlobField label="Emblema" id="time-emblema" name="emblema" data-cy="emblema" isImage accept="image/*" />
              <ValidatedBlobField label="Uniforme" id="time-uniforme" name="uniforme" data-cy="uniforme" isImage accept="image/*" />
              <ValidatedField label="Nome Clube" id="time-nomeClube" name="nomeClube" data-cy="nomeClube" type="text" />
              <ValidatedField
                label="Titulos Brasileiro"
                id="time-titulosBrasileiro"
                name="titulosBrasileiro"
                data-cy="titulosBrasileiro"
                type="text"
              />
              <ValidatedField
                label="Titulos Estadual"
                id="time-titulosEstadual"
                name="titulosEstadual"
                data-cy="titulosEstadual"
                type="text"
              />
              <ValidatedField
                label="Titulos Libertadores"
                id="time-titulosLibertadores"
                name="titulosLibertadores"
                data-cy="titulosLibertadores"
                type="text"
              />
              <ValidatedField label="Titulos Mundial" id="time-titulosMundial" name="titulosMundial" data-cy="titulosMundial" type="text" />
              <ValidatedField
                label="Maior Artilheiro"
                id="time-maiorArtilheiro"
                name="maiorArtilheiro"
                data-cy="maiorArtilheiro"
                type="text"
              />
              <ValidatedField label="Estado Origem" id="time-estadoOrigem" name="estadoOrigem" data-cy="estadoOrigem" type="select">
                {estadoOrigemValues.map(estadoOrigem => (
                  <option value={estadoOrigem} key={estadoOrigem}>
                    {estadoOrigem}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Treinador" id="time-treinador" name="treinador" data-cy="treinador" type="text" />
              <ValidatedField label="Presidente" id="time-presidente" name="presidente" data-cy="presidente" type="text" />
              <ValidatedField label="Ano Fundacao" id="time-anoFundacao" name="anoFundacao" data-cy="anoFundacao" type="date" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/time" replace color="info">
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

export default TimeUpdate;
