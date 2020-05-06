import { RegisterComponent } from './../account/register/register.component';
import { Routes } from '@angular/router';

import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, metricsRoute, trackerRoute, userMgmtRoute } from './';

import { UserRouteAccessService } from 'app/core';
import { activateRoute, passwordRoute, passwordResetFinishRoute, passwordResetInitRoute, registerRoute, settingsRoute } from 'app/account';

const ADMIN_ROUTES = [
  auditsRoute,
  configurationRoute,
  docsRoute,
  healthRoute,
  logsRoute,
  trackerRoute,
  ...userMgmtRoute,
  metricsRoute,
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  passwordResetInitRoute,
  registerRoute,
  settingsRoute
];

export const adminState: Routes = [
  {
    path: '',
    data: {
      authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
  }
];
