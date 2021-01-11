import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { StoreExtendedService } from '../store.service';
import { StoreNewExtendedComponent } from '../new/store-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { StoreListComponent } from 'src/app/entities/store/index';

@Component({
  selector: 'app-store-list',
  templateUrl: './store-list.component.html',
  styleUrls: ['./store-list.component.scss'],
})
export class StoreListExtendedComponent extends StoreListComponent implements OnInit {
  title: string = 'Store';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public storeService: StoreExtendedService,
    public errorService: ErrorService,
    public addressService: AddressExtendedService,
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
      storeService,
      errorService,
      addressService,
      staffService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(StoreNewExtendedComponent);
  }
}
