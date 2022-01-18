import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAccessibilityGroupComponent } from './manage-accessibility-group.component';

describe('ManageAccessibilityGroupComponent', () => {
  let component: ManageAccessibilityGroupComponent;
  let fixture: ComponentFixture<ManageAccessibilityGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageAccessibilityGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageAccessibilityGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
