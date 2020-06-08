//export const pathPrincipal = 'http://localhost:8080/api/';
export const pathPrincipal = 'https://dev-cabasto.sharktech.com.mx/api/';
export const pathChat = 'https://dev-cabasto.sharktech.com.mx/';
//export const pathChat = 'http://localhost:8080/';

export const appCliente = 1;
export const appProveedor = 2;
export const appTransportista = 3;

export const environment = {
  production: true,
  productos: `${pathPrincipal}productos`,
  productosCategoria: `${pathPrincipal}productos/home`,
  proveedorProductos: `${pathPrincipal}proveedor-productos`,
  categoria: `${pathPrincipal}proveedor-productos/categoria/`,
  secciones: `${pathPrincipal}seccions`,
  categorias: `${pathPrincipal}categorias`,
  proveedores: `${pathPrincipal}proveedors`,

  notificaciones: `${pathPrincipal}notificaciones`,
  transportista: `${pathPrincipal}transportista`,
  proveedoresFull: `${pathPrincipal}proveedores`,
  proveedor: `${pathPrincipal}proveedor`,
  proveedoresProducto: `${pathPrincipal}proveedores/producto/`,

  registro: `${pathPrincipal}register`,
  login: `${pathPrincipal}authenticate`,
  carritoCompras: `${pathPrincipal}carrito-compras`,
  carritoHistorico: `${pathPrincipal}carrito-historicos`,
  carritoHistoricoDetalle: `${pathPrincipal}carrito-historico-detalles`,
  getImagenIndividual: `${pathPrincipal}adjuntos/download/`,
  promociones: `${pathPrincipal}promociones`,
  tarjetas: `${pathPrincipal}tarjetas`,
  direcciones: `${pathPrincipal}usuario-direcciones`,
  tipoDirecciones: `${pathPrincipal}tipo-direcciones`,
  pedidos: `${pathPrincipal}pedidos`,

  timeline: `${pathPrincipal}historico-pedido-proveedores/pedido-proveedor/`,

  pedidosAdmin: `${pathPrincipal}seguimiento-pedidos`,
  estatus: `${pathPrincipal}estatus/tipoEstatus/ESTATUS_PEDIDO`,

  pedidosProveedor: `${pathPrincipal}proveedor/pedidos`,
  pedidosTransportista: `${pathPrincipal}transportista/pedidos`,

  pedidosProveedores: `${pathPrincipal}proveedor/pedido-proveedores`,
  pedidosTransportistas: `${pathPrincipal}transportista/pedido-proveedores`,

  calificacionServicio: `${pathPrincipal}pedido-proveedores/calificacion-servicio`,
  carritoHistoricosProveedor: `${pathPrincipal}carrito-historicos-proveedores`,
  usuarios: `${pathPrincipal}usuarios`,

  usuarioDocumentos: `${pathPrincipal}usuario-documentos`,

  users: `${pathPrincipal}users`,

  chats: `${pathPrincipal}chats`,

  chatsProveedor: `${pathPrincipal}proveedor/chats/pedido-proveedor/`,

  cambioContraseña: `${pathPrincipal}account/change-password`,

  carritoComprasProveedor: `${pathPrincipal}carrito-compras-proveedor`,

  reset: `${pathPrincipal}account/reset-password/init`,
  logout: null,
  icons: {
    persona: {
      icon: '../../../content/imgs/direcciones/m3.png'
    },
    casa: {
      icon: '../../../content/imgs/direcciones/m2.png'
    },
    lugar: {
      icon: '../../../content/imgs/direcciones/m1.png'
    },
    proveedor: {
      icon: '../../../content/imgs/direcciones/m4.png'
    }
  },

  //info de GOOGLE
  geocodeGoogle: 'https://maps.googleapis.com/maps/api/geocode/json',
  keyGoogle: 'AIzaSyDpg-WwghYJCwSq1Q8nM_5ZW5IY5tLNFmQ',

  //Fines de pruebas
  emulado: true,

  //Aqui "cambiamos" la app en ejecución
  perfil: {
    activo: appCliente
    //activo: appProveedor
    //activo: appTransportista
  },

  st: {
    keyPublic: 'U2FsdGVkX19CQc0Np+So9tyR3R9dAm7lOeyk2UQ+FoHcjsmxFAcZES1Hix101zBa1gljuF7xoHmJQVXb6oP6Mg=='
    //keyPublic: 'U2FsdGVkX1/ADpxluaklCuGOBDdLHN6q44K8U8mHKBbCF95IBvllQPUxmSiAyj9hqImPuFlYzLS2MUFJU9ZOdg==',
  },

  firebase: {
    apiKey: 'AIzaSyDpg-WwghYJCwSq1Q8nM_5ZW5IY5tLNFmQ',
    authDomain: 'shark-abasto-275623.firebaseapp.com',
    databaseURL: 'https://shark-abasto-275623.firebaseio.com',
    projectId: 'shark-abasto-275623',
    storageBucket: 'shark-abasto-275623.appspot.com',
    messagingSenderId: '183752745939',
    appId: '1:183752745939:web:29b89ad4bf08fdcc0c8526',
    measurementId: 'G-FTHW54PP2W'
  }
};
