import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditcharacterizationComponent } from './editcharacterization.component';

describe('EditcharacterizationComponent', () => {
  let component: EditcharacterizationComponent;
  let fixture: ComponentFixture<EditcharacterizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditcharacterizationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditcharacterizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
