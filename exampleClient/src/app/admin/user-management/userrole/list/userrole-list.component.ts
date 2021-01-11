import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IUserrole } from '../iuserrole';
import { UserroleService } from '../userrole.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UserroleNewComponent } from '../new/userrole-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { RoleService } from 'src/app/admin/user-management/role/role.service';
import { UserService } from 'src/app/admin/user-management/user/user.service';

@Component({
  selector: 'app-userrole-list',
  templateUrl: './userrole-list.component.html',
  styleUrls: ['./userrole-list.component.scss'],
})
export class UserroleListComponent extends BaseListComponent<IUserrole> implements OnInit {
  title = 'Userrole';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public userroleService: UserroleService,
    public errorService: ErrorService,
    public roleService: RoleService,
    public userService: UserService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, userroleService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Userrole';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['roleId', 'userId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'roleId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'roleDescriptiveField',
        referencedDescriptiveField: 'displayName',
        service: this.roleService,
        associatedObj: undefined,
        table: 'role',
        type: 'ManyToOne',
        url: 'Userroles',
        listColumn: 'role',
        label: 'role',
      },
      {
        column: [
          {
            key: 'userId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'userDescriptiveField',
        referencedDescriptiveField: 'userName',
        service: this.userService,
        associatedObj: undefined,
        table: 'user',
        type: 'ManyToOne',
        url: 'userroles',
        listColumn: 'user',
        label: 'user',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'roleDescriptiveField',
        searchColumn: 'role',
        label: 'role',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'userDescriptiveField',
        searchColumn: 'user',
        label: 'user',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'actions',
        label: 'Actions',
        sort: false,
        filter: false,
        type: listColumnType.String,
      },
    ];
    this.selectedColumns = this.columns;
    this.displayedColumns = this.columns.map((obj) => {
      return obj.column;
    });
  }
  addNew(comp) {
    if (!comp) {
      comp = UserroleNewComponent;
    }
    super.addNew(comp);
  }
}
