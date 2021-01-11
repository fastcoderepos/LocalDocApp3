import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { TestingModule } from 'src/testing/utils';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user';
import { UpdateProfileExtendedComponent } from './update-profile.component';
import { DateUtils } from 'src/app/common/shared';

describe('UpdateProfileExtendedComponent', () => {
  let component: UpdateProfileExtendedComponent;
  let fixture: ComponentFixture<UpdateProfileExtendedComponent>;
  let el: HTMLElement;

  let d = new Date();
  let t = DateUtils.formatDateStringToAMPM(d);
  let data = {
    emailAddress: 'emailAddress1@test.com',
    firstName: 'firstName1',
    lastName: 'lastName1',
    phoneNumber: 'phoneNumber1',
    userName: 'userName1',
  };

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UpdateProfileExtendedComponent],
        imports: [TestingModule],
        providers: [UserExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UpdateProfileExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
