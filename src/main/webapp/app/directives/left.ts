import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[left]'
})
export class Left {
  el: ElementRef;
  constructor(el: ElementRef) {
    this.el = el;
  }

  ngAfterViewInit() {
    const hostElem = this.el.nativeElement;
    hostElem.children[0].style.left = '30px';
  }
}
