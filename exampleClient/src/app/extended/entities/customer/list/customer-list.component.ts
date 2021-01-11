import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { CustomerExtendedService } from '../customer.service';
import { CustomerNewExtendedComponent } from '../new/customer-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { CustomerListComponent } from 'src/app/entities/customer/index';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss'],
})
export class CustomerListExtendedComponent extends CustomerListComponent implements OnInit {
  title: string = 'Customer';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public customerService: CustomerExtendedService,
    public errorService: ErrorService,
    public addressService: AddressExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      customerService,
      errorService,
      addressService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(CustomerNewExtendedComponent);
  }
}
