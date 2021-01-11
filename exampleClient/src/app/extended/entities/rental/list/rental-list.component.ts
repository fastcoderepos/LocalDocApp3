import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { RentalExtendedService } from '../rental.service';
import { RentalNewExtendedComponent } from '../new/rental-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { InventoryExtendedService } from 'src/app/extended/entities/inventory/inventory.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { RentalListComponent } from 'src/app/entities/rental/index';

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.scss'],
})
export class RentalListExtendedComponent extends RentalListComponent implements OnInit {
  title: string = 'Rental';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public rentalService: RentalExtendedService,
    public errorService: ErrorService,
    public customerService: CustomerExtendedService,
    public inventoryService: InventoryExtendedService,
    public staffService: StaffExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      rentalService,
      errorService,
      customerService,
      inventoryService,
      staffService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(RentalNewExtendedComponent);
  }
}
