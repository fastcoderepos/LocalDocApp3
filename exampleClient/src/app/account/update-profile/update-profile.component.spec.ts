import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Location } from '@angular/common';

import { TestingModule } from 'src/testing/utils';
import { UserService } from 'src/app/admin/user-management/user';
import { UpdateProfileComponent } from './update-profile.component';
import { DateUtils } from 'src/app/common/shared';

describe('UpdateProfileComponent', () => {
  let component: UpdateProfileComponent;
  let fixture: ComponentFixture<UpdateProfileComponent>;
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
        declarations: [UpdateProfileComponent],
        imports: [TestingModule],
        providers: [UserService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UpdateProfileComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should run #ngOnInit()', async () => {
      spyOn(component.userService, 'getProfile').and.returnValue(of(data));
      component.ngOnInit();

      expect(component.user).toEqual(data);
      expect(component.userForm.getRawValue()).toEqual(data);
      component.userForm.enable();
      expect(component.userForm.valid).toEqual(true);
    });

    it('should run getItem and handle error response', async () => {
      spyOn(component.userService, 'getProfile').and.returnValue(of(data));

      component.getItem();
      fixture.detectChanges();

      expect(component.user).toEqual(data);
      expect(component.userForm.getRawValue()).toEqual(data);
    });

    it('should run getItem and handle error response', async () => {
      spyOn(component.userService, 'getProfile').and.callFake(() => {
        return throwError('error occurred');
      });
      spyOn(component.errorService, 'showError').and.returnValue();

      component.getItem();
      fixture.detectChanges();

      expect(component.errorService.showError).toHaveBeenCalled();
    });

    it('should run onSubmit and handle success response', async () => {
      spyOn(component.errorService, 'showError').and.returnValue();
      spyOn(component.userService, 'updateProfile').and.returnValue(of(data));

      component.user = data;
      component.userForm.patchValue(data);
      component.userForm.enable();
      fixture.detectChanges();

      el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
      el.click();

      expect(component.errorService.showError).toHaveBeenCalled();
      expect(component.loading).toBe(false);
    });

    it('should run onSubmit and handle error response', async () => {
      spyOn(component.errorService, 'showError').and.returnValue();
      spyOn(component.userService, 'updateProfile').and.callFake((token) => {
        return throwError('error occurred');
      });

      component.user = data;
      component.userForm.patchValue(data);
      component.userForm.enable();
      fixture.detectChanges();

      el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
      el.click();

      expect(component.errorService.showError).toHaveBeenCalled();
      expect(component.loading).toBe(false);
    });

    it('should navigate to root when back button is clicked', async () => {
      const router = TestBed.get(Router);
      const location = TestBed.get(Location);
      let navigationSpy = spyOn(router, 'navigate').and.callThrough();

      component.user = data;
      component.userForm.patchValue(data);
      component.userForm.enable();
      fixture.detectChanges();

      el = fixture.debugElement.query(By.css('button[name=back]')).nativeElement;
      el.click();

      let responsePromise = navigationSpy.calls.mostRecent().returnValue;
      await responsePromise;
      expect(location.path()).toBe('/');
    });
  });
});
