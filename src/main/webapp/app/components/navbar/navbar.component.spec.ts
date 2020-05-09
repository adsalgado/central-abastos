import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponentMain } from './navbar.component';

describe('NavbarComponentMain', () => {
  let component: NavbarComponentMain;
  let fixture: ComponentFixture<NavbarComponentMain>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarComponentMain]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponentMain);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
