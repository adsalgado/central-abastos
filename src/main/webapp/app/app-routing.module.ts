import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { HomeComponent } from './home';
import { HomePublicComponent } from './pages/home-layout/home-public.component';
import { LoginComponent } from './pages/login/login.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AbastosAdminModule)
  },
  { path: '', redirectTo: 'Home', data: { title: 'Home' }, pathMatch: 'full' },

  { path: 'Home', component: HomeComponent },

  {
    path: 'main',
    component: HomePublicComponent,
    children: [{ path: 'login', component: LoginComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AbastosAppRoutingModule {}
