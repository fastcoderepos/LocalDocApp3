import { Component, OnInit, Inject } from '@angular/core';
import { AddressExtendedService } from '../address.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CityExtendedService } from 'src/app/extended/entities/city/city.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { AddressNewComponent } from 'src/app/entities/address/index';

@Component({
  selector: 'app-address-new',
  templateUrl: './address-new.component.html',
  styleUrls: ['./address-new.component.scss'],
})
export class AddressNewExtendedComponent extends AddressNewComponent implements OnInit {
  title: string = 'New Address';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<AddressNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public addressExtendedService: AddressExtendedService,
    public errorService: ErrorService,
    public cityExtendedService: CityExtendedService,
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
      addressExtendedService,
      errorService,
      cityExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
