import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenDocViewComponent } from './citizen-doc-view.component';

describe('CitizenDocViewComponent', () => {
  let component: CitizenDocViewComponent;
  let fixture: ComponentFixture<CitizenDocViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenDocViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CitizenDocViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
