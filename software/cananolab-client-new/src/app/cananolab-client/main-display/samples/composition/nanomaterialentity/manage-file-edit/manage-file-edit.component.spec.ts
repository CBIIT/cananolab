import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageFileEditComponent } from './manage-file-edit.component';

describe('ManageFileEditComponent', () => {
  let component: ManageFileEditComponent;
  let fixture: ComponentFixture<ManageFileEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageFileEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageFileEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
