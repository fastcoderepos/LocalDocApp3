import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IStore } from '../istore';
import { StoreService } from '../store.service';
import { Router, ActivatedRoute } from '@angular/router';
import { StoreNewComponent } from '../new/store-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { AddressService } from 'src/app/entities/address/address.service';
import { StaffService } from 'src/app/entities/staff/staff.service';

@Component({
  selector: 'app-store-list',
  templateUrl: './store-list.component.html',
  styleUrls: ['./store-list.component.scss'],
})
export class StoreListComponent extends BaseListComponent<IStore> implements OnInit {
  title = 'Store';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public storeService: StoreService,
    public errorService: ErrorService,
    public addressService: AddressService,
    public staffService: StaffService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, storeService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Store';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['storeId'];
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
        url: 'stores',
        listColumn: 'address',
        label: 'address',
      },
      {
        column: [
          {
            key: 'managerStaffId',
            value: undefined,
            referencedkey: 'staffId',
          },
        ],
        isParent: false,
        descriptiveField: 'staffDescriptiveField',
        referencedDescriptiveField: 'staffId',
        service: this.staffService,
        associatedObj: undefined,
        table: 'staff',
        type: 'ManyToOne',
        url: 'stores',
        listColumn: 'staff',
        label: 'staff',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'lastUpdate',
        searchColumn: 'lastUpdate',
        label: 'last Update',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
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
        column: 'addressDescriptiveField',
        searchColumn: 'address',
        label: 'address',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'staffDescriptiveField',
        searchColumn: 'staff',
        label: 'staff',
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
      comp = StoreNewComponent;
    }
    super.addNew(comp);
  }
}
