import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJogador } from 'app/shared/model/jogador.model';
import { getEntities } from './jogador.reducer';

export const Jogador = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const jogadorList = useAppSelector(state => state.jogador.entities);
  const loading = useAppSelector(state => state.jogador.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="jogador-heading" data-cy="JogadorHeading">
        Jogadors
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/jogador/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Jogador
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jogadorList && jogadorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Foto</th>
                <th>Idade</th>
                <th>Posicao</th>
                <th>Camisa</th>
                <th>Numerode Gols</th>
                <th>Time</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jogadorList.map((jogador, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/jogador/${jogador.id}`} color="link" size="sm">
                      {jogador.id}
                    </Button>
                  </td>
                  <td>{jogador.nome}</td>
                  <td>
                    {jogador.foto ? (
                      <div>
                        {jogador.fotoContentType ? (
                          <a onClick={openFile(jogador.fotoContentType, jogador.foto)}>
                            <img src={`data:${jogador.fotoContentType};base64,${jogador.foto}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {jogador.fotoContentType}, {byteSize(jogador.foto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{jogador.idade}</td>
                  <td>{jogador.posicao}</td>
                  <td>{jogador.camisa}</td>
                  <td>{jogador.numerodeGols}</td>
                  <td>{jogador.time ? <Link to={`/time/${jogador.time.id}`}>{jogador.time.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/jogador/${jogador.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/jogador/${jogador.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/jogador/${jogador.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Jogadors found</div>
        )}
      </div>
    </div>
  );
};

export default Jogador;
