import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Jogador from './jogador';
import JogadorDetail from './jogador-detail';
import JogadorUpdate from './jogador-update';
import JogadorDeleteDialog from './jogador-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JogadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JogadorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JogadorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Jogador} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JogadorDeleteDialog} />
  </>
);

export default Routes;
