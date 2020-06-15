import { Component, OnInit, ViewChild, NgZone } from '@angular/core';
import { NavController } from 'ionic-angular';
import { MessagingService } from 'app/services/messaging.service';
import { Queja } from 'app/shared/model/queja.model';
import { Subject } from 'rxjs';
import { MatTableDataSource, MatSort, MatSidenav } from '@angular/material';
import { ReclamoDialogComponent } from 'app/shared/reclamo-dialog/reclamo-dialog.component';
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

  columnsToDisplay = ['claimId', 'fullName', 'profile', 'numeroPedido', 'status'];

  constructor(private messagingService: MessagingService, private zone: NgZone) /* private apiService : ApiService*/ {}

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
    this.messagingService.receiveMessage();
    //TODO: Get data from real source.
    this.dataSource.data = [
      {
        claimId: 1,
        createdAt: moment('2020-06-05', 'YYYY-MM-DD'),
        lastUpdated: moment('2020-06-05', 'YYYY-MM-DD'),
        tracking: [],
        status: 'abierto',
        requestedBy: {
          fullName: 'Alberto Cruz',
          profile: 'provider'
        },
        claimMessage: 'Las naranjas que me trajo están podridas, no las quiero!',
        numeroPedido: 1234567
      },
      {
        claimId: 2,
        createdAt: moment('2020-06-05', 'YYYY-MM-DD'),
        lastUpdated: moment('2020-06-05', 'YYYY-MM-DD'),
        tracking: [
          {
            date: moment('2020-06-05', 'YYYY-MM-DD'),
            description: 'Me puse en contacto con el proveedor para que pasara por la manzana',
            name: 'Roberto Fallas'
          }
        ],
        status: 'abierto',
        requestedBy: {
          fullName: 'Juanito Johns Cruz',
          profile: 'distributor'
        },
        claimMessage: 'No iba la manzana en el pedido',
        numeroPedido: 24681012
      },
      {
        claimId: 3,
        createdAt: moment('2020-06-05', 'YYYY-MM-DD'),
        lastUpdated: moment('2020-06-05', 'YYYY-MM-DD'),
        tracking: [
          {
            date: moment('2020-06-05', 'YYYY-MM-DD'),
            description: 'Me puse en contacto con el proveedor para que pasara por la manzana',
            name: 'Juanito Johns'
          },
          {
            date: moment('2020-06-05', 'YYYY-MM-DD'),
            description: 'El proveedor regresó por la manzana y ahora va en camino',
            name: 'Juanito Johns'
          }
        ],
        status: 'cerrado',
        requestedBy: {
          fullName: 'Claudia Alzate',
          profile: 'customer'
        },
        claimMessage: 'No iba la manzana en el pedido',
        numeroPedido: 24681012
      }
    ];

    /*this.apiService.getClaims()
        .subscribe( (resp : any) => {
            // this.claims = resp.claims;
             this.dataSource.data = resp.claims;
        },
        (error) => {
          console.log(error);
        });*/
  }

  onRowSelected(row) {
    this.claimSelected = row;
    this.sideNav.toggle();
  }
}
