import { Component, OnInit } from '@angular/core';
import { NavController } from 'ionic-angular';
import { MessagingService } from 'app/services/messaging.service';
import { Queja } from 'app/shared/model/queja.model';

@Component({
  selector: 'page-center',
  templateUrl: 'center.html',
  styleUrls: ['./center.scss']
})
export class CenterPage implements OnInit {
  constructor(private messagingService: MessagingService) {}

  ngOnInit(): void {
    this.messagingService.requestPermission();
  }

  ngAfterViewInit(): void {
    this.messagingService.currentMessage.subscribe((claim: Queja) => {
      console.log(claim.id);
    });
  }
}
