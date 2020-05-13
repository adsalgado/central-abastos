import { environment } from '../../../environments/environment.prod';
import { Menu } from './../../models/Menu';
import { Component, OnInit } from '@angular/core';

import * as $ from 'jquery';
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'dashboard', class: '' },
  { path: '/user-profile', title: 'User Profile', icon: 'person', class: '' },
  { path: '/table-list', title: 'Table List', icon: 'content_paste', class: '' },
  { path: '/typography', title: 'Typography', icon: 'library_books', class: '' },
  { path: '/icons', title: 'Icons', icon: 'bubble_chart', class: '' },
  { path: '/maps', title: 'Maps', icon: 'location_on', class: '' },
  { path: '/notifications', title: 'Notifications', icon: 'notifications', class: '' },
  { path: '/upgrade', title: 'Upgrade to PRO', icon: 'unarchive', class: 'active-pro' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  public menu: Menu[] = [];
  constructor() {}

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    switch (environment.perfil.activo) {
      case 1:
        this.menu.push(new Menu('Mi perfil', false, '../../../content/imgs/perfil/social-media.png', '/perfil', null));
        this.menu.push(new Menu('Lista de carrito frecuentes', false, '../../../content/imgs/lista-carrito/trolley.png', '#7d3a63', null));
        this.menu.push(new Menu('Direcciones frecuentes', false, '../../../content/imgs/direcciones/markerD.png', '#7d3a63', null));
        this.menu.push(new Menu('Mi historial', false, '../../../content/imgs/menu/historial.png', '#7d3a63', null));

        this.menu.push(new Menu('Acerca de', false, '../../../content/imgs/menu/interface.png', '#7d3a63', null));
        this.menu.push(new Menu('Información de la app', false, '../../../content/imgs/menu/signs.png', '#7d3a63', null));
        this.menu.push(new Menu('Contacto', false, '../../../content/imgs/menu/logotype.png', '#7d3a63', null));
        this.menu.push(new Menu('Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '#7d3a63', null));
        break;

      case 2:
        this.menu.push(new Menu('Acerca de', false, '../../../content/imgs/menu/interface.png', '#7d3a63', null));
        this.menu.push(new Menu('Información de la app', false, '../../../content/imgs/menu/signs.png', '#7d3a63', null));
        this.menu.push(new Menu('Contacto', false, '../../../content/imgs/menu/logotype.png', '#7d3a63', null));
        this.menu.push(new Menu('Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '#7d3a63', null));
        break;
    }
  }
  isMobileMenu() {
    /* if ($(window).width() > 991) {
      return false;
    } */
    return true;
  }
}
