import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderCitizenComponent } from './header-citizen.component';

describe('HeaderCitizenComponent', () => {
  let component: HeaderCitizenComponent;
  let fixture: ComponentFixture<HeaderCitizenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderCitizenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderCitizenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
