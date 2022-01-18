import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitPublicationComponent } from './submit-publication.component';

describe('SubmitPublicationComponent', () => {
  let component: SubmitPublicationComponent;
  let fixture: ComponentFixture<SubmitPublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitPublicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
