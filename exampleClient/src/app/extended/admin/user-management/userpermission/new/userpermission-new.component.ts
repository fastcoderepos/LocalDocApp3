import { Component, OnInit, Inject } from '@angular/core';
import { UserpermissionExtendedService } from '../userpermission.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { PermissionExtendedService } from 'src/app/extended/admin/user-management/permission/permission.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { UserpermissionNewComponent } from 'src/app/admin/user-management/userpermission/index';

@Component({
  selector: 'app-userpermission-new',
  templateUrl: './userpermission-new.component.html',
  styleUrls: ['./userpermission-new.component.scss'],
})
export class UserpermissionNewExtendedComponent extends UserpermissionNewComponent implements OnInit {
  title: string = 'New Userpermission';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<UserpermissionNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public userpermissionExtendedService: UserpermissionExtendedService,
    public errorService: ErrorService,
    public permissionExtendedService: PermissionExtendedService,
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
      userpermissionExtendedService,
      errorService,
      permissionExtendedService,
      userExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
