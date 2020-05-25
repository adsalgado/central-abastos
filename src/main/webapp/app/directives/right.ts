import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[right]'
})
export class Right {
  el: ElementRef;
  constructor(el: ElementRef) {
    this.el = el;
  }

  ngAfterViewInit() {
    const hostElem = this.el.nativeElement;
    hostElem.children[0].style.right = '30px';
  }
}
