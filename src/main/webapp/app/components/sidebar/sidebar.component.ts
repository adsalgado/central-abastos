import { JhiEventManager } from 'ng-jhipster';
import { environment } from '../../../environments/environment.prod';
import { Menu } from './../../models/Menu';
import { Component, OnInit } from '@angular/core';

import * as $ from 'jquery';
import { NavParamsService } from 'app/services/nav-params.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
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
  public evento: any = null;

  constructor(
    private navParams: NavParamsService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    let user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    if (user) {
      this.menu = [];
      this.armaMenu(user);
    }

    this.evento = this.eventManager.subscribe('armaMenu', response => {
      let user = this.localStorageEncryptService.getFromLocalStorage('userSession');
      if (user) {
        this.menu = [];
        this.armaMenu(user);
      }
    });
  }

  armaMenu(user: any) {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    if (!user.tipo_usuario) {
      user.tipo_usuario = 1;
    }
    console.log(user);

    switch (user.tipo_usuario) {
      case 1:
        this.menu.push(new Menu('Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos-proveedor', null));
        break;
      case 2:
        this.menu.push(new Menu('Mi perfil', false, '../../../content/imgs/perfil/social-media.png', '/main/perfil', null));
        this.menu.push(
          new Menu('Lista de carrito frecuentes', false, '../../../content/imgs/lista-carrito/trolley.png', '/main/lista-carrito', null)
        );
        this.menu.push(new Menu('Tarjetas', false, '../../../content/imgs/menu/card.png', '/main/tarjeta-frecuente', null));
        this.menu.push(
          new Menu('Direcciones frecuentes', false, '../../../content/imgs/direcciones/markerD.png', '/main/direccion-frecuente', null)
        );
        this.menu.push(new Menu('Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos', null));
        this.menu.push(new Menu('Proveedores', false, '../../../content/imgs/menu/give.png', '/main/proveedores', null));

        this.menu.push(new Menu('Acerca de', false, '../../../content/imgs/menu/interface.png', '/main/acercade', null));
        this.menu.push(new Menu('Información de la app', false, '../../../content/imgs/menu/signs.png', '/main/informacion', null));
        this.menu.push(new Menu('Contacto', false, '../../../content/imgs/menu/logotype.png', '/main/contacto', null));
        this.menu.push(
          new Menu('Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '/main/terminos-condiciones', null)
        );
        break;

      case 3:
        //this.menu.push(new Menu("Mi perfil", "assets/imgs/perfil/social-media.png", "#7d3a63", PerfilPage));
        this.menu.push(new Menu('Mi perfil', false, '../../../content/imgs/perfil/social-media.png', '/main/perfil', null));
        this.menu.push(new Menu('Mis documentos', false, '../../../content/imgs/menu/file.png', '/main/documentos-proveedor', null));

        this.menu.push(new Menu('Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos-proveedor', null));

        this.menu.push(new Menu('Acerca de', false, '../../../content/imgs/menu/interface.png', '/main/acercade', null));
        this.menu.push(new Menu('Información de la app', false, '../../../content/imgs/menu/signs.png', '/main/informacion', null));
        this.menu.push(new Menu('Contacto', false, '../../../content/imgs/menu/logotype.png', '/main/contacto', null));
        this.menu.push(
          new Menu('Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '/main/terminos-condiciones', null)
        );

        this.navParams.push('/main/pedidos-proveedor');
        break;
    }
  }

  isMobileMenu() {
    /* if ($(window).width() > 991) {
      return false;
    } */
    return true;
  }

  goTo(menu: Menu) {
    this.navParams.push(menu.route);
  }
}
