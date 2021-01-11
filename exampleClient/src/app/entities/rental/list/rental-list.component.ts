import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IRental } from '../irental';
import { RentalService } from '../rental.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RentalNewComponent } from '../new/rental-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CustomerService } from 'src/app/entities/customer/customer.service';
import { InventoryService } from 'src/app/entities/inventory/inventory.service';
import { StaffService } from 'src/app/entities/staff/staff.service';

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.scss'],
})
export class RentalListComponent extends BaseListComponent<IRental> implements OnInit {
  title = 'Rental';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public rentalService: RentalService,
    public errorService: ErrorService,
    public customerService: CustomerService,
    public inventoryService: InventoryService,
    public staffService: StaffService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, rentalService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Rental';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['rentalId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'customerId',
            value: undefined,
            referencedkey: 'customerId',
          },
        ],
        isParent: false,
        descriptiveField: 'customerDescriptiveField',
        referencedDescriptiveField: 'customerId',
        service: this.customerService,
        associatedObj: undefined,
        table: 'customer',
        type: 'ManyToOne',
        url: 'rentals',
        listColumn: 'customer',
        label: 'customer',
      },
      {
        column: [
          {
            key: 'inventoryId',
            value: undefined,
            referencedkey: 'inventoryId',
          },
        ],
        isParent: false,
        descriptiveField: 'inventoryDescriptiveField',
        referencedDescriptiveField: 'inventoryId',
        service: this.inventoryService,
        associatedObj: undefined,
        table: 'inventory',
        type: 'ManyToOne',
        url: 'rentals',
        listColumn: 'inventory',
        label: 'inventory',
      },
      {
        column: [
          {
            key: 'staffId',
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
        url: 'rentals',
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
        column: 'rentalDate',
        searchColumn: 'rentalDate',
        label: 'rental Date',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'rentalId',
        searchColumn: 'rentalId',
        label: 'rental Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'returnDate',
        searchColumn: 'returnDate',
        label: 'return Date',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'customerDescriptiveField',
        searchColumn: 'customer',
        label: 'customer',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'inventoryDescriptiveField',
        searchColumn: 'inventory',
        label: 'inventory',
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
      comp = RentalNewComponent;
    }
    super.addNew(comp);
  }
}
