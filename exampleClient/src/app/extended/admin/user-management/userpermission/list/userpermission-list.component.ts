import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { UserpermissionExtendedService } from '../userpermission.service';
import { UserpermissionNewExtendedComponent } from '../new/userpermission-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { PermissionExtendedService } from 'src/app/extended/admin/user-management/permission/permission.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { UserpermissionListComponent } from 'src/app/admin/user-management/userpermission/index';

@Component({
  selector: 'app-userpermission-list',
  templateUrl: './userpermission-list.component.html',
  styleUrls: ['./userpermission-list.component.scss'],
})
export class UserpermissionListExtendedComponent extends UserpermissionListComponent implements OnInit {
  title: string = 'Userpermission';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public userpermissionService: UserpermissionExtendedService,
    public errorService: ErrorService,
    public permissionService: PermissionExtendedService,
    public userService: UserExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      userpermissionService,
      errorService,
      permissionService,
      userService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(UserpermissionNewExtendedComponent);
  }
}
