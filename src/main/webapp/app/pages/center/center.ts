import { Component, OnInit, ViewChild, NgZone, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Queja, IQueja } from 'app/shared/model/queja.model';
import { Subject } from 'rxjs';
import { MatTableDataSource, MatSort, MatSidenav } from '@angular/material';
import { ReclamoDialogComponent } from 'app/shared/reclamo-dialog/reclamo-dialog.component';
import { QuejaService } from 'app/entities/queja';
import { TrackingQueja } from 'app/models/tracking-queja';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NgForm } from '@angular/forms';
const moment = require('moment');

@Component({
  selector: 'page-center',
  templateUrl: 'center.html',
  styleUrls: ['./center.scss']
})
export class CenterPage implements OnInit {
  showButton = true;
  claimSelected: Queja = null;
  onClaimReceived = new Subject<String>();
  dataSource = new MatTableDataSource<Queja>();

  @ViewChild(MatSort, { static: false }) sort: MatSort;
  @ViewChild('sidenavDetails', { static: false }) sideNav: MatSidenav;

  columnsToDisplay = ['id', 'usuario', 'profile', 'folioPedido', 'createdDate', 'status'];

  constructor(
    private apiService: QuejaService,
    private localStorageService: LocalStorageEncryptService,
    private changeDetector: ChangeDetectorRef
  ) {}

  ngAfterViewInit(): void {
    this.dataSource.filterPredicate = (data, filter) => {
      const pattern =
        data.id +
        data.usuario.firstName +
        data.usuario.lastName +
        data.tipoUsuario.nombre +
        data.pedidoProveedor.folio +
        data.fechaAlta +
        data.estatus.nombre;
      return pattern
        .trim()
        .toLowerCase()
        .includes(filter);
    };
  }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.apiService.query().subscribe((response: any) => {
      this.dataSource.data = response.body;
    });
  }

  onRowSelected(row) {
    this.claimSelected = row;
    this.changeDetector.detectChanges();
    this.sideNav.toggle();

    //this.openModal(this.claimSelected);
  }

  saveTracking(trackingForm: NgForm) {
    const user = this.localStorageService.getFromLocalStorage('userSession');
    const trackingToSave = new TrackingQueja(
      moment().format('YYYY-MM-DD HH:mm'),
      trackingForm.value.trackingText,
      user,
      this.claimSelected.id
    );
    console.log(trackingToSave);
    this.apiService.addTracking(trackingToSave).subscribe(
      (resp: TrackingQueja) => {
        resp.isNew = true;
        this.claimSelected.tracking.push(resp);
        trackingForm.reset();
      },
      error => {
        moment().format('YYYY-MM-DD HH:mm');
        console.log(error);
      }
    );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  changeClaimStatus(newStatus: string) {
    this.claimSelected.estatus.nombre = newStatus;
    const fechaTmp = this.claimSelected.fechaAlta;
    this.claimSelected.fechaAlta = null;
    this.apiService.updateStatus(this.claimSelected).subscribe(
      (resp: Queja) => {
        console.log(resp);
        this.claimSelected.fechaAlta = fechaTmp;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
}
