import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComposingElementFormComponent } from './composing-element-form.component';

describe('ComposingElementFormComponent', () => {
  let component: ComposingElementFormComponent;
  let fixture: ComponentFixture<ComposingElementFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComposingElementFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComposingElementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
