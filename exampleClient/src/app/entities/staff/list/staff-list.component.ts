import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IStaff } from '../istaff';
import { StaffService } from '../staff.service';
import { Router, ActivatedRoute } from '@angular/router';
import { StaffNewComponent } from '../new/staff-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { AddressService } from 'src/app/entities/address/address.service';

@Component({
  selector: 'app-staff-list',
  templateUrl: './staff-list.component.html',
  styleUrls: ['./staff-list.component.scss'],
})
export class StaffListComponent extends BaseListComponent<IStaff> implements OnInit {
  title = 'Staff';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public staffService: StaffService,
    public errorService: ErrorService,
    public addressService: AddressService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, staffService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Staff';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['staffId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'addressId',
            value: undefined,
            referencedkey: 'addressId',
          },
        ],
        isParent: false,
        descriptiveField: 'addressDescriptiveField',
        referencedDescriptiveField: 'addressId',
        service: this.addressService,
        associatedObj: undefined,
        table: 'address',
        type: 'ManyToOne',
        url: 'staffs',
        listColumn: 'address',
        label: 'address',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'active',
        searchColumn: 'active',
        label: 'active',
        sort: true,
        filter: true,
        type: listColumnType.Boolean,
      },
      {
        column: 'email',
        searchColumn: 'email',
        label: 'email',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'firstName',
        searchColumn: 'firstName',
        label: 'first Name',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'lastName',
        searchColumn: 'lastName',
        label: 'last Name',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'lastUpdate',
        searchColumn: 'lastUpdate',
        label: 'last Update',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'password',
        searchColumn: 'password',
        label: 'password',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'staffId',
        searchColumn: 'staffId',
        label: 'staff Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'storeId',
        searchColumn: 'storeId',
        label: 'store Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'username',
        searchColumn: 'username',
        label: 'username',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'addressDescriptiveField',
        searchColumn: 'address',
        label: 'address',
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
      comp = StaffNewComponent;
    }
    super.addNew(comp);
  }
}
