import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './jogador.reducer';

export const JogadorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const jogadorEntity = useAppSelector(state => state.jogador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jogadorDetailsHeading">Jogador</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{jogadorEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{jogadorEntity.nome}</dd>
          <dt>
            <span id="foto">Foto</span>
          </dt>
          <dd>
            {jogadorEntity.foto ? (
              <div>
                {jogadorEntity.fotoContentType ? (
                  <a onClick={openFile(jogadorEntity.fotoContentType, jogadorEntity.foto)}>
                    <img src={`data:${jogadorEntity.fotoContentType};base64,${jogadorEntity.foto}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {jogadorEntity.fotoContentType}, {byteSize(jogadorEntity.foto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="idade">Idade</span>
          </dt>
          <dd>{jogadorEntity.idade}</dd>
          <dt>
            <span id="posicao">Posicao</span>
          </dt>
          <dd>{jogadorEntity.posicao}</dd>
          <dt>
            <span id="camisa">Camisa</span>
          </dt>
          <dd>{jogadorEntity.camisa}</dd>
          <dt>
            <span id="numerodeGols">Numerode Gols</span>
          </dt>
          <dd>{jogadorEntity.numerodeGols}</dd>
          <dt>Time</dt>
          <dd>{jogadorEntity.time ? jogadorEntity.time.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/jogador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/jogador/${jogadorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default JogadorDetail;
