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
      case 5:
        this.menu.push(
          new Menu('1-m1', 'Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos-proveedor', null)
        );
        this.menu.push(
          new Menu(
            '1-m2',
            'Seguimiento a Quejas',
            false,
            '../../../content/imgs/contact-center/technical-support.png',
            '/main/contact-center',
            null
          )
        );
        this.navParams.push('/main/pedidos-proveedor');
        break;
      case 1:
        this.menu.push(
          new Menu('1-m3', 'Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos-proveedor', null)
        );
        this.menu.push(
          new Menu('1-m4', 'Catálogos', false, '../../../content/imgs/menu/historial.png', null, [
            new Menu('1-sm4', 'Promociones', false, '../../../content/imgs/menu/historial.png', '/main/entities/promocion', null),
            new Menu('1-sm4', 'Unidad Medida', false, '../../../content/imgs/menu/historial.png', '/main/entities/unidad-medida', null),
            new Menu('1-sm4', 'Tipo Artículo', false, '../../../content/imgs/menu/historial.png', '/main/entities/tipo-articulo', null),
            new Menu('1-sm4', 'Categoría', false, '../../../content/imgs/menu/historial.png', '/main/entities/categoria', null),
            new Menu('1-sm4', 'Sección', false, '../../../content/imgs/menu/historial.png', '/main/entities/seccion', null),
            new Menu('1-sm4', 'Estatus', false, '../../../content/imgs/menu/historial.png', '/main/entities/estatus', null),
            new Menu('1-sm4', 'Proveedor', false, '../../../content/imgs/menu/historial.png', '/main/entities/proveedor', null),
            new Menu('1-sm4', 'Transportista', false, '../../../content/imgs/menu/historial.png', '/main/entities/transportista', null),
            new Menu(
              '1-sm4',
              'Parámetros aplicación',
              false,
              '../../../content/imgs/menu/historial.png',
              '/main/entities/parametros-aplicacion',
              null
            ),
            new Menu('1-sm4', 'Empresa', false, '../../../content/imgs/menu/historial.png', '/main/entities/empresa', null)
          ])
        );

        this.menu.push(
          new Menu('1-m35', 'Administración', false, '../../../content/imgs/menu/historial.png', null, [
            new Menu(
              '1-sm4',
              'Gestión de usuarios',
              false,
              '../../../content/imgs/menu/historial.png',
              '/main/admin/user-management',
              null
            ),
            new Menu('1-sm4', 'User Tracker', false, '../../../content/imgs/menu/historial.png', '/main/admin/jhi-tracker', null),
            new Menu('1-sm4', 'Métricas', false, '../../../content/imgs/menu/historial.png', '/main/admin/jhi-metrics', null),

            new Menu('1-sm4', 'Salud', false, '../../../content/imgs/menu/historial.png', '/main/admin/jhi-health', null),
            new Menu('1-sm4', 'Configuración', false, '../../../content/imgs/menu/historial.png', '/main/admin/audits', null),
            new Menu('1-sm4', 'Métricas', false, '../../../content/imgs/menu/historial.png', '/main/admin/logs', null),
            new Menu('1-sm4', 'Métricas', false, '../../../content/imgs/menu/historial.png', '/main/admin/docs', null)
          ])
        );

        this.menu.push(
          new Menu('1-m36', 'Reportes', false, '../../../content/imgs/menu/historial.png', null, [
            new Menu('1-sm4', 'Costo proveedor', false, '../../../content/imgs/menu/historial.png', '/main/reportes/reporte-costos', null)
          ])
        );
        this.navParams.push('/main/pedidos-proveedor');
        break;
      case 2:
        this.menu.push(new Menu('1-m5', 'Mi perfil', false, '../../../content/imgs/perfil/social-media.png', '/main/perfil', null));
        this.menu.push(
          new Menu(
            '1-m6',
            'Lista de carrito frecuentes',
            false,
            '../../../content/imgs/lista-carrito/trolley.png',
            '/main/lista-carrito',
            null
          )
        );
        this.menu.push(new Menu('1-m7', 'Tarjetas', false, '../../../content/imgs/menu/card.png', '/main/tarjeta-frecuente', null));
        this.menu.push(
          new Menu(
            '1-m8',
            'Direcciones frecuentes',
            false,
            '../../../content/imgs/direcciones/markerD.png',
            '/main/direccion-frecuente',
            null
          )
        );
        this.menu.push(new Menu('1-m9', 'Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos', null));
        this.menu.push(new Menu('1-m10', 'Proveedores', false, '../../../content/imgs/menu/give.png', '/main/proveedores', null));

        this.menu.push(new Menu('1-m11', 'Acerca de', false, '../../../content/imgs/menu/interface.png', '/main/acercade', null));
        this.menu.push(
          new Menu('1-m12', 'Información de la app', false, '../../../content/imgs/menu/signs.png', '/main/informacion', null)
        );
        this.menu.push(new Menu('1-m13', 'Contacto', false, '../../../content/imgs/menu/logotype.png', '/main/contacto', null));
        this.menu.push(
          new Menu('1-m14', 'Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '/main/terminos-condiciones', null)
        );
        break;

      case 3:
        //this.menu.push(new Menu("Mi perfil", "assets/imgs/perfil/social-media.png", "#7d3a63", PerfilPage));
        this.menu.push(new Menu('1-m15', 'Mi perfil', false, '../../../content/imgs/perfil/social-media.png', '/main/perfil', null));
        this.menu.push(
          new Menu('1-m16', 'Mis documentos', false, '../../../content/imgs/menu/file.png', '/main/documentos-proveedor', null)
        );

        this.menu.push(
          new Menu('1-m17', 'Mi historial', false, '../../../content/imgs/menu/historial.png', '/main/pedidos-proveedor', null)
        );

        this.menu.push(new Menu('1-m18', 'Acerca de', false, '../../../content/imgs/menu/interface.png', '/main/acercade', null));
        this.menu.push(
          new Menu('1-m19', 'Información de la app', false, '../../../content/imgs/menu/signs.png', '/main/informacion', null)
        );
        this.menu.push(new Menu('1-m20', 'Contacto', false, '../../../content/imgs/menu/logotype.png', '/main/contacto', null));
        this.menu.push(
          new Menu('1-m21', 'Términos y condiciones', false, '../../../content/imgs/menu/contrato.png', '/main/terminos-condiciones', null)
        );

        this.menu.push(
          new Menu('1-m467', 'Catálogos', false, '../../../content/imgs/menu/historial.png', null, [
            new Menu(
              '1-sm478',
              'Productos proveedores',
              false,
              '../../../content/imgs/menu/historial.png',
              '/main/entities/producto-proveedor',
              null
            )
          ])
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
