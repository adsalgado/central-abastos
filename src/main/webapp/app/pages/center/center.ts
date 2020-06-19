import { Component, OnInit, ViewChild, NgZone } from '@angular/core';
import { NavController } from 'ionic-angular';
import { MessagingService } from 'app/services/messaging.service';
import { Queja, IQueja } from 'app/shared/model/queja.model';
import { Subject } from 'rxjs';
import { MatTableDataSource, MatSort, MatSidenav } from '@angular/material';
import { ReclamoDialogComponent } from 'app/shared/reclamo-dialog/reclamo-dialog.component';
import { QuejaService } from 'app/entities/queja';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { TrackingQueja } from 'app/models/tracking-queja';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
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

  @ViewChild('modalClaim', { static: false }) modal: ReclamoDialogComponent;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('sidenavDetails', { static: false }) sideNav: MatSidenav;

  columnsToDisplay = ['id', 'usuario', 'profile', 'folioPedido', 'createdDate', 'status'];

  constructor(
    private messagingService: MessagingService,
    private zone: NgZone,
    private apiService: QuejaService,
    private localStorageService: LocalStorageEncryptService
  ) {}

  ngAfterViewInit(): void {
    this.messagingService.currentMessage.subscribe(claim => {
      this.openModal(claim);
    });
  }

  openModal(claim: Queja) {
    this.modal.open(claim);
  }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.messagingService.requestPermission();
    this.messagingService.receiveMessage();

    this.apiService.query().subscribe((response: any) => {
      this.dataSource.data = response.body;
      console.log(response.body);
    });
  }

  onRowSelected(row) {
    this.claimSelected = row;
    this.sideNav.toggle();
  }

  saveTracking(trackingText) {
    const user = this.localStorageService.getFromLocalStorage('userSession');
    const trackingToSave = new TrackingQueja(moment().format('YYYY-MM-DD HH:mm'), trackingText, user, this.claimSelected.id);
    console.log(trackingToSave);
    this.apiService.addTracking(trackingToSave).subscribe(
      resp => {
        console.log(resp);
      },
      error => {
        console.log(error);
      }
    );
  }
}
