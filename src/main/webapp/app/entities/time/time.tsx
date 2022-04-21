import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITime } from 'app/shared/model/time.model';
import { getEntities } from './time.reducer';

export const Time = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const timeList = useAppSelector(state => state.time.entities);
  const loading = useAppSelector(state => state.time.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="time-heading" data-cy="TimeHeading">
        Times
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/time/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Time
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {timeList && timeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Emblema</th>
                <th>Uniforme</th>
                <th>Nome Clube</th>
                <th>Titulos Brasileiro</th>
                <th>Titulos Estadual</th>
                <th>Titulos Libertadores</th>
                <th>Titulos Mundial</th>
                <th>Maior Artilheiro</th>
                <th>Estado Origem</th>
                <th>Treinador</th>
                <th>Presidente</th>
                <th>Ano Fundacao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {timeList.map((time, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/time/${time.id}`} color="link" size="sm">
                      {time.id}
                    </Button>
                  </td>
                  <td>
                    {time.emblema ? (
                      <div>
                        {time.emblemaContentType ? (
                          <a onClick={openFile(time.emblemaContentType, time.emblema)}>
                            <img src={`data:${time.emblemaContentType};base64,${time.emblema}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {time.emblemaContentType}, {byteSize(time.emblema)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {time.uniforme ? (
                      <div>
                        {time.uniformeContentType ? (
                          <a onClick={openFile(time.uniformeContentType, time.uniforme)}>
                            <img src={`data:${time.uniformeContentType};base64,${time.uniforme}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {time.uniformeContentType}, {byteSize(time.uniforme)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{time.nomeClube}</td>
                  <td>{time.titulosBrasileiro}</td>
                  <td>{time.titulosEstadual}</td>
                  <td>{time.titulosLibertadores}</td>
                  <td>{time.titulosMundial}</td>
                  <td>{time.maiorArtilheiro}</td>
                  <td>{time.estadoOrigem}</td>
                  <td>{time.treinador}</td>
                  <td>{time.presidente}</td>
                  <td>{time.anoFundacao ? <TextFormat type="date" value={time.anoFundacao} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/time/${time.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/time/${time.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/time/${time.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Times found</div>
        )}
      </div>
    </div>
  );
};

export default Time;
