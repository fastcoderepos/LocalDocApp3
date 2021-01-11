import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { UserpermissionExtendedService } from '../userpermission.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { PermissionExtendedService } from 'src/app/extended/admin/user-management/permission/permission.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { UserpermissionDetailsComponent } from 'src/app/admin/user-management/userpermission/index';

@Component({
  selector: 'app-userpermission-details',
  templateUrl: './userpermission-details.component.html',
  styleUrls: ['./userpermission-details.component.scss'],
})
export class UserpermissionDetailsExtendedComponent extends UserpermissionDetailsComponent implements OnInit {
  title: string = 'Userpermission';
  parentUrl: string = 'userpermission';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public userpermissionExtendedService: UserpermissionExtendedService,
    public pickerDialogService: PickerDialogService,
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
      global,
      userpermissionExtendedService,
      pickerDialogService,
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
