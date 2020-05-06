export const pathPrincipal = 'http://localhost:8080/api/';
export const pathChat = 'http://localhost:8080/';
export const environment = {
  production: true,
  productos: `${pathPrincipal}productos`,
  productosCategoria: `${pathPrincipal}productos/home`,
  proveedorProductos: `${pathPrincipal}proveedor-productos`,
  categoria: `${pathPrincipal}proveedor-productos/categoria/`,
  secciones: `${pathPrincipal}seccions`,
  categorias: `${pathPrincipal}categorias`,
  proveedores: `${pathPrincipal}proveedors`,

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
  logout: null,
  icons: {
    persona: {
      icon: 'assets/imgs/direcciones/m3.png'
    },
    casa: {
      icon: 'assets/imgs/direcciones/m2.png'
    },
    lugar: {
      icon: 'assets/imgs/direcciones/m1.png'
    },
    proveedor: {
      icon: 'assets/imgs/direcciones/m4.png'
    }
  },

  //info de GOOGLE
  geocodeGoogle: 'https://maps.googleapis.com/maps/api/geocode/json',
  keyGoogle: 'AIzaSyDpg-WwghYJCwSq1Q8nM_5ZW5IY5tLNFmQ',

  //Fines de pruebas
  emulado: true
};
