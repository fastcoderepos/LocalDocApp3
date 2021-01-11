import { Component, OnInit, Inject } from '@angular/core';
import { UserroleExtendedService } from '../userrole.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { RoleExtendedService } from 'src/app/extended/admin/user-management/role/role.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { UserroleNewComponent } from 'src/app/admin/user-management/userrole/index';

@Component({
  selector: 'app-userrole-new',
  templateUrl: './userrole-new.component.html',
  styleUrls: ['./userrole-new.component.scss'],
})
export class UserroleNewExtendedComponent extends UserroleNewComponent implements OnInit {
  title: string = 'New Userrole';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<UserroleNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public userroleExtendedService: UserroleExtendedService,
    public errorService: ErrorService,
    public roleExtendedService: RoleExtendedService,
    public userExtendedService: UserExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      dialogRef,
      data,
      global,
      pickerDialogService,
      userroleExtendedService,
      errorService,
      roleExtendedService,
      userExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
