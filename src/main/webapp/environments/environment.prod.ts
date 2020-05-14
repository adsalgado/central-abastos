//export const pathPrincipal = "http://localhost:8080/api/";
export const pathPrincipal = 'https://dev-cabasto.sharktech.com.mx/api/';
export const pathChat = 'https://dev-cabasto.sharktech.com.mx/';
//export const pathChat = "http://localhost:8080/";

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

  proveedoresFull: `${pathPrincipal}proveedores`,

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
  pedidosProveedor: `${pathPrincipal}proveedor/pedidos`,
  pedidosTransportista: `${pathPrincipal}transportista/pedidos`,

  pedidosProveedores: `${pathPrincipal}proveedor/pedido-proveedores`,
  pedidosTransportistas: `${pathPrincipal}transportista/pedido-proveedores`,

  usuarios: `${pathPrincipal}usuarios`,

  usuarioDocumentos: `${pathPrincipal}usuario-documentos`,

  users: `${pathPrincipal}users`,

  chats: `${pathPrincipal}chats`,

  chatsProveedor: `${pathPrincipal}proveedor/chats/pedido-proveedor/`,

  cambioContraseña: `${pathPrincipal}account/change-password`,
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

  stripe: {
    keyPublic: 'pk_test_TNjRZggfGMHinhrlBVIP1P1B00d8WURtiI'
    //keyPublic: 'pk_live_4f4ddGQitsEeJ0I1zg84xkRZ00mUNujYXd',

    //keyPrivate: 'sk_live_fcKnhw5seaKkY2ERdjJcKBOC007a6LoXl0',
    //keyPrivate: 'sk_test_BDQpRihwXwK00K7EN1aMifQc00CHosOopt',
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
