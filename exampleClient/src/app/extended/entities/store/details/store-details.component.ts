import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { StoreExtendedService } from '../store.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { StoreDetailsComponent } from 'src/app/entities/store/index';

@Component({
  selector: 'app-store-details',
  templateUrl: './store-details.component.html',
  styleUrls: ['./store-details.component.scss'],
})
export class StoreDetailsExtendedComponent extends StoreDetailsComponent implements OnInit {
  title: string = 'Store';
  parentUrl: string = 'store';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public storeExtendedService: StoreExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public addressExtendedService: AddressExtendedService,
    public staffExtendedService: StaffExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      storeExtendedService,
      pickerDialogService,
      errorService,
      addressExtendedService,
      staffExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
