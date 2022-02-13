import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderMedicalOfficialComponent } from './header-medical-official.component';

describe('HeaderMedicalOfficialComponent', () => {
  let component: HeaderMedicalOfficialComponent;
  let fixture: ComponentFixture<HeaderMedicalOfficialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderMedicalOfficialComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderMedicalOfficialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
