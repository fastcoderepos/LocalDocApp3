import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { UserroleExtendedService } from '../userrole.service';
import { UserroleNewExtendedComponent } from '../new/userrole-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { RoleExtendedService } from 'src/app/extended/admin/user-management/role/role.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { UserroleListComponent } from 'src/app/admin/user-management/userrole/index';

@Component({
  selector: 'app-userrole-list',
  templateUrl: './userrole-list.component.html',
  styleUrls: ['./userrole-list.component.scss'],
})
export class UserroleListExtendedComponent extends UserroleListComponent implements OnInit {
  title: string = 'Userrole';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public userroleService: UserroleExtendedService,
    public errorService: ErrorService,
    public roleService: RoleExtendedService,
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
      userroleService,
      errorService,
      roleService,
      userService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(UserroleNewExtendedComponent);
  }
}
