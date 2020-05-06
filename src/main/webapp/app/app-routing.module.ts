import { ProductoComponent } from 'app/entities/producto/producto.component';
import { UserMgmtComponent } from 'app/admin/user-management/user-management.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { HomeComponent } from './home';
import { HomePublicComponent } from './pages/home-layout/home-public.component';
import { LoginComponent } from './pages/login/login.component';
import { HomePublicMainComponent } from './pages/home-public/home-public-main.component';
import { UserRouteAccessService } from './core';
import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, trackerRoute, userMgmtRoute, metricsRoute } from './admin';
import { JhiLoginModalComponent } from './shared/login/login.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];
const ADMIN_ROUTES = [auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, trackerRoute, ...userMgmtRoute, metricsRoute];
const routes: Routes = [
  { path: '', redirectTo: 'main/public-home', data: { title: 'Home' }, pathMatch: 'full' },
  { path: 'main', redirectTo: 'main/public-home', data: { title: 'Home' }, pathMatch: 'full' },
  { path: 'Home', component: HomeComponent },

  {
    path: 'main',
    component: HomePublicComponent,
    children: [
      { path: 'public-home', component: HomePublicMainComponent },
      { path: 'login', component: JhiLoginModalComponent },
      { path: 'home', component: HomeComponent },
      { path: 'users', component: UserMgmtComponent },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AbastosAdminModule)
      },
      {
        path: 'entities',
        loadChildren: () => import('./entities/entity.module').then(m => m.AbastosEntityModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AbastosAppRoutingModule {}
