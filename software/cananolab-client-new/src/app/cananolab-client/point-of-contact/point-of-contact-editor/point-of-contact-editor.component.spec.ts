import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PointOfContactEditorComponent } from './point-of-contact-editor.component';

describe('PointOfContactEditorComponent', () => {
  let component: PointOfContactEditorComponent;
  let fixture: ComponentFixture<PointOfContactEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PointOfContactEditorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointOfContactEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
