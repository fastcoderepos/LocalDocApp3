import { Component, OnInit, Inject } from '@angular/core';
import { StaffExtendedService } from '../staff.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { StaffNewComponent } from 'src/app/entities/staff/index';

@Component({
  selector: 'app-staff-new',
  templateUrl: './staff-new.component.html',
  styleUrls: ['./staff-new.component.scss'],
})
export class StaffNewExtendedComponent extends StaffNewComponent implements OnInit {
  title: string = 'New Staff';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<StaffNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public staffExtendedService: StaffExtendedService,
    public errorService: ErrorService,
    public addressExtendedService: AddressExtendedService,
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
      staffExtendedService,
      errorService,
      addressExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
