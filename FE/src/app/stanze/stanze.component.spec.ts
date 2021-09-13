import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StanzeComponent } from './stanze.component';

describe('StanzeComponent', () => {
  let component: StanzeComponent;
  let fixture: ComponentFixture<StanzeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StanzeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StanzeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
