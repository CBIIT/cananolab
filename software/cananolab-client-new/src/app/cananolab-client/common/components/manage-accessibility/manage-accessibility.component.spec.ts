import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAccessibilityComponent } from './manage-accessibility.component';

describe('ManageAccessibilityComponent', () => {
  let component: ManageAccessibilityComponent;
  let fixture: ComponentFixture<ManageAccessibilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageAccessibilityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageAccessibilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
