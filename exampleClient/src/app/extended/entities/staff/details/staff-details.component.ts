import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { StaffExtendedService } from '../staff.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { StaffDetailsComponent } from 'src/app/entities/staff/index';

@Component({
  selector: 'app-staff-details',
  templateUrl: './staff-details.component.html',
  styleUrls: ['./staff-details.component.scss'],
})
export class StaffDetailsExtendedComponent extends StaffDetailsComponent implements OnInit {
  title: string = 'Staff';
  parentUrl: string = 'staff';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public staffExtendedService: StaffExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public addressExtendedService: AddressExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      staffExtendedService,
      pickerDialogService,
      errorService,
      addressExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
