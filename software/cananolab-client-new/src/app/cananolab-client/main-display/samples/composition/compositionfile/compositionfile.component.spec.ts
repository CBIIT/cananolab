import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompositionfileComponent } from './compositionfile.component';

describe('CompositionfileComponent', () => {
  let component: CompositionfileComponent;
  let fixture: ComponentFixture<CompositionfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompositionfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompositionfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
