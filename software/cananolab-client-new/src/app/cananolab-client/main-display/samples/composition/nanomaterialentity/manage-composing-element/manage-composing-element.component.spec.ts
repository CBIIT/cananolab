import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageComposingElementComponent } from './manage-composing-element.component';

describe('ManageComposingElementComponent', () => {
  let component: ManageComposingElementComponent;
  let fixture: ComponentFixture<ManageComposingElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageComposingElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageComposingElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
