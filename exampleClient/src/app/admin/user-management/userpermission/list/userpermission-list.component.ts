import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IUserpermission } from '../iuserpermission';
import { UserpermissionService } from '../userpermission.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UserpermissionNewComponent } from '../new/userpermission-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { PermissionService } from 'src/app/admin/user-management/permission/permission.service';
import { UserService } from 'src/app/admin/user-management/user/user.service';

@Component({
  selector: 'app-userpermission-list',
  templateUrl: './userpermission-list.component.html',
  styleUrls: ['./userpermission-list.component.scss'],
})
export class UserpermissionListComponent extends BaseListComponent<IUserpermission> implements OnInit {
  title = 'Userpermission';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public userpermissionService: UserpermissionService,
    public errorService: ErrorService,
    public permissionService: PermissionService,
    public userService: UserService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, userpermissionService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Userpermission';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['permissionId', 'userId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'permissionId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'permissionDescriptiveField',
        referencedDescriptiveField: 'displayName',
        service: this.permissionService,
        associatedObj: undefined,
        table: 'permission',
        type: 'ManyToOne',
        url: 'Userpermissions',
        listColumn: 'permission',
        label: 'permission',
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
        url: 'userpermissions',
        listColumn: 'user',
        label: 'user',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'revoked',
        searchColumn: 'revoked',
        label: 'revoked',
        sort: true,
        filter: true,
        type: listColumnType.Boolean,
      },
      {
        column: 'permissionDescriptiveField',
        searchColumn: 'permission',
        label: 'permission',
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
      comp = UserpermissionNewComponent;
    }
    super.addNew(comp);
  }
}
