import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IAddress } from '../iaddress';
import { AddressService } from '../address.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AddressNewComponent } from '../new/address-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CityService } from 'src/app/entities/city/city.service';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.scss'],
})
export class AddressListComponent extends BaseListComponent<IAddress> implements OnInit {
  title = 'Address';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public addressService: AddressService,
    public errorService: ErrorService,
    public cityService: CityService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, addressService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Address';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['addressId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'cityId',
            value: undefined,
            referencedkey: 'cityId',
          },
        ],
        isParent: false,
        descriptiveField: 'cityDescriptiveField',
        referencedDescriptiveField: 'cityId',
        service: this.cityService,
        associatedObj: undefined,
        table: 'city',
        type: 'ManyToOne',
        url: 'address',
        listColumn: 'city',
        label: 'city',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'address',
        searchColumn: 'address',
        label: 'address',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'address2',
        searchColumn: 'address2',
        label: 'address 2',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'addressId',
        searchColumn: 'addressId',
        label: 'address Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'district',
        searchColumn: 'district',
        label: 'district',
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
        column: 'phone',
        searchColumn: 'phone',
        label: 'phone',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'postalCode',
        searchColumn: 'postalCode',
        label: 'postal Code',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'cityDescriptiveField',
        searchColumn: 'city',
        label: 'city',
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
      comp = AddressNewComponent;
    }
    super.addNew(comp);
  }
}
