import { Injectable } from '@angular/core';
import { OverlayRef, Overlay } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { SpinnerOverlayComponent } from '../components/spinner-overlay/spinner-overlay.component';

@Injectable()
export class LoadingService {
  private overlayRef: OverlayRef = null;
  private activo: boolean = false;

  constructor(private overlay: Overlay) {}
  //
  public show(message = '') {
    // Returns an OverlayRef (which is a PortalHost)

    if (!this.activo) {
      if (!this.overlayRef) {
        this.overlayRef = this.overlay.create();
      }

      // Create ComponentPortal that can be attached to a PortalHost
      const spinnerOverlayPortal = new ComponentPortal(SpinnerOverlayComponent);
      const component = this.overlayRef.attach(spinnerOverlayPortal); // Attach ComponentPortal to PortalHost
    }
  }

  public hide() {
    if (this.activo) {
      if (!!this.overlayRef) {
        this.overlayRef.detach();
      }
    }
  }
}
