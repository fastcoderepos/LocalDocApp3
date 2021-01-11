import { Component, OnInit, Inject } from '@angular/core';
import { StoreExtendedService } from '../store.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { StoreNewComponent } from 'src/app/entities/store/index';

@Component({
  selector: 'app-store-new',
  templateUrl: './store-new.component.html',
  styleUrls: ['./store-new.component.scss'],
})
export class StoreNewExtendedComponent extends StoreNewComponent implements OnInit {
  title: string = 'New Store';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<StoreNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public storeExtendedService: StoreExtendedService,
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
      dialogRef,
      data,
      global,
      pickerDialogService,
      storeExtendedService,
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
