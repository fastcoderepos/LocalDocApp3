import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ICustomer } from '../icustomer';
import { CustomerService } from '../customer.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CustomerNewComponent } from '../new/customer-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { AddressService } from 'src/app/entities/address/address.service';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss'],
})
export class CustomerListComponent extends BaseListComponent<ICustomer> implements OnInit {
  title = 'Customer';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public customerService: CustomerService,
    public errorService: ErrorService,
    public addressService: AddressService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, customerService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Customer';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['customerId'];
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
        url: 'customers',
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
        type: listColumnType.Number,
      },
      {
        column: 'activebool',
        searchColumn: 'activebool',
        label: 'activebool',
        sort: true,
        filter: true,
        type: listColumnType.Boolean,
      },
      {
        column: 'createDate',
        searchColumn: 'createDate',
        label: 'create Date',
        sort: true,
        filter: true,
        type: listColumnType.Date,
      },
      {
        column: 'customerId',
        searchColumn: 'customerId',
        label: 'customer Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
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
      comp = CustomerNewComponent;
    }
    super.addNew(comp);
  }
}
