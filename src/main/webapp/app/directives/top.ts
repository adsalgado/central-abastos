import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[top]'
})
export class Top {
  el: ElementRef;
  constructor(el: ElementRef) {
    this.el = el;
  }

  ngAfterViewInit() {
    const hostElem = this.el.nativeElement;
    hostElem.children[0].style.top = '40px';
  }
}
