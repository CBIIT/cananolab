import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChemicalassociationComponent } from './chemicalassociation.component';

describe('ChemicalassociationComponent', () => {
  let component: ChemicalassociationComponent;
  let fixture: ComponentFixture<ChemicalassociationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChemicalassociationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChemicalassociationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
