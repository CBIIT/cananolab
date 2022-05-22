import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditpublicationComponent } from './editpublication.component';

describe('EditpublicationComponent', () => {
  let component: EditpublicationComponent;
  let fixture: ComponentFixture<EditpublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditpublicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditpublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
