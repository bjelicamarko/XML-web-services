import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReasonToDeclineComponent } from './reason-to-decline.component';

describe('ReasonToDeclineComponent', () => {
  let component: ReasonToDeclineComponent;
  let fixture: ComponentFixture<ReasonToDeclineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReasonToDeclineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReasonToDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
