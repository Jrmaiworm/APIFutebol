import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Time from './time';
import TimeDetail from './time-detail';
import TimeUpdate from './time-update';
import TimeDeleteDialog from './time-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TimeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TimeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TimeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Time} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TimeDeleteDialog} />
  </>
);

export default Routes;
