import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public slides: any = [
    { img: 'http://placehold.it/350x150/000000' },
    { img: 'http://placehold.it/350x150/111111' },
    { img: 'http://placehold.it/350x150/333333' },
    { img: 'http://placehold.it/350x150/666666' }
  ];

  public dynamicDots: any = `dots${Math.floor(Math.random() + 999999)}`;
  public leftArrowsSlider: any = `left-arrw-${Math.floor(Math.random() + 999999)}`;
  public rightArrowsSlider: any = `right-arrw-${Math.floor(Math.random() + 999999)}`;

  public slideConfig: any = {
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    appendDots: `.${this.dynamicDots}`,
    dots: true,
    autoplaySpeed: 3000,
    pauseOnDotsHover: true,
    infinite: true,
    focusOnSelect: true,
    nextArrow: "<div class='nav-btn next-slide'></div>",
    prevArrow: "<div class='nav-btn prev-slide'></div>"
  };

  constructor() {}

  ngOnInit() {}

  addSlide() {
    this.slides.push({ img: 'http://placehold.it/350x150/777777' });
  }

  removeSlide() {
    this.slides.length = this.slides.length - 1;
  }

  slickInit(e) {
    console.log('slick initialized');
  }

  breakpoint(e) {
    console.log('breakpoint');
  }

  afterChange(e) {
    console.log('afterChange');
  }

  beforeChange(e) {
    console.log('beforeChange');
  }
}
