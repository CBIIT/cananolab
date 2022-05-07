import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FunctionalizingentityComponent } from './functionalizingentity.component';

describe('FunctionalizingentityComponent', () => {
  let component: FunctionalizingentityComponent;
  let fixture: ComponentFixture<FunctionalizingentityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FunctionalizingentityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FunctionalizingentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
