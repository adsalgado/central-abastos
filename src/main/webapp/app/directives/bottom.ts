import { Directive, ElementRef, OnInit } from '@angular/core';

@Directive({
  selector: '[bottom]'
})
export class Bottom {
  el: ElementRef;
  constructor(el: ElementRef) {
    this.el = el;
  }

  ngAfterViewInit() {
    const hostElem = this.el.nativeElement;
    hostElem.children[0].style.bottom = '40px';
  }
}
