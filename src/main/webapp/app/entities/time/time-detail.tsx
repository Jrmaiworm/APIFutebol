import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './time.reducer';

export const TimeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const timeEntity = useAppSelector(state => state.time.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="timeDetailsHeading">Time</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{timeEntity.id}</dd>
          <dt>
            <span id="emblema">Emblema</span>
          </dt>
          <dd>
            {timeEntity.emblema ? (
              <div>
                {timeEntity.emblemaContentType ? (
                  <a onClick={openFile(timeEntity.emblemaContentType, timeEntity.emblema)}>
                    <img src={`data:${timeEntity.emblemaContentType};base64,${timeEntity.emblema}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {timeEntity.emblemaContentType}, {byteSize(timeEntity.emblema)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="uniforme">Uniforme</span>
          </dt>
          <dd>
            {timeEntity.uniforme ? (
              <div>
                {timeEntity.uniformeContentType ? (
                  <a onClick={openFile(timeEntity.uniformeContentType, timeEntity.uniforme)}>
                    <img src={`data:${timeEntity.uniformeContentType};base64,${timeEntity.uniforme}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {timeEntity.uniformeContentType}, {byteSize(timeEntity.uniforme)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="nomeClube">Nome Clube</span>
          </dt>
          <dd>{timeEntity.nomeClube}</dd>
          <dt>
            <span id="titulosBrasileiro">Titulos Brasileiro</span>
          </dt>
          <dd>{timeEntity.titulosBrasileiro}</dd>
          <dt>
            <span id="titulosEstadual">Titulos Estadual</span>
          </dt>
          <dd>{timeEntity.titulosEstadual}</dd>
          <dt>
            <span id="titulosLibertadores">Titulos Libertadores</span>
          </dt>
          <dd>{timeEntity.titulosLibertadores}</dd>
          <dt>
            <span id="titulosMundial">Titulos Mundial</span>
          </dt>
          <dd>{timeEntity.titulosMundial}</dd>
          <dt>
            <span id="maiorArtilheiro">Maior Artilheiro</span>
          </dt>
          <dd>{timeEntity.maiorArtilheiro}</dd>
          <dt>
            <span id="estadoOrigem">Estado Origem</span>
          </dt>
          <dd>{timeEntity.estadoOrigem}</dd>
          <dt>
            <span id="treinador">Treinador</span>
          </dt>
          <dd>{timeEntity.treinador}</dd>
          <dt>
            <span id="presidente">Presidente</span>
          </dt>
          <dd>{timeEntity.presidente}</dd>
          <dt>
            <span id="anoFundacao">Ano Fundacao</span>
          </dt>
          <dd>
            {timeEntity.anoFundacao ? <TextFormat value={timeEntity.anoFundacao} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/time" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/time/${timeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TimeDetail;
