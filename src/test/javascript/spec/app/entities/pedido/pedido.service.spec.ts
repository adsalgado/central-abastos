/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PedidoService } from 'app/entities/pedido/pedido.service';
import { IPedido, Pedido } from 'app/shared/model/pedido.model';

describe('Service Tests', () => {
  describe('Pedido Service', () => {
    let injector: TestBed;
    let service: PedidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPedido;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PedidoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pedido(0, 0, 0, 0, 0, currentDate, currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaPedido: currentDate.format(DATE_FORMAT),
            fechaPreparacion: currentDate.format(DATE_FORMAT),
            fechaCobro: currentDate.format(DATE_FORMAT),
            fechaEntrega: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Pedido', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaPedido: currentDate.format(DATE_FORMAT),
            fechaPreparacion: currentDate.format(DATE_FORMAT),
            fechaCobro: currentDate.format(DATE_FORMAT),
            fechaEntrega: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPreparacion: currentDate,
            fechaCobro: currentDate,
            fechaEntrega: currentDate
          },
          returnedFromService
        );
        service
          .create(new Pedido(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Pedido', async () => {
        const returnedFromService = Object.assign(
          {
            totalSinIva: 1,
            comisionTransportista: 1,
            comisionPreparador: 1,
            total: 1,
            fechaPedido: currentDate.format(DATE_FORMAT),
            fechaPreparacion: currentDate.format(DATE_FORMAT),
            fechaCobro: currentDate.format(DATE_FORMAT),
            fechaEntrega: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPreparacion: currentDate,
            fechaCobro: currentDate,
            fechaEntrega: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Pedido', async () => {
        const returnedFromService = Object.assign(
          {
            totalSinIva: 1,
            comisionTransportista: 1,
            comisionPreparador: 1,
            total: 1,
            fechaPedido: currentDate.format(DATE_FORMAT),
            fechaPreparacion: currentDate.format(DATE_FORMAT),
            fechaCobro: currentDate.format(DATE_FORMAT),
            fechaEntrega: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaPedido: currentDate,
            fechaPreparacion: currentDate,
            fechaCobro: currentDate,
            fechaEntrega: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pedido', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
