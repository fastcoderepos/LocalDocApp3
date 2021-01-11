import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { StaffExtendedService } from '../staff.service';
import { StaffNewExtendedComponent } from '../new/staff-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { AddressExtendedService } from 'src/app/extended/entities/address/address.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { StaffListComponent } from 'src/app/entities/staff/index';

@Component({
  selector: 'app-staff-list',
  templateUrl: './staff-list.component.html',
  styleUrls: ['./staff-list.component.scss'],
})
export class StaffListExtendedComponent extends StaffListComponent implements OnInit {
  title: string = 'Staff';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public staffService: StaffExtendedService,
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
      staffService,
      errorService,
      addressService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(StaffNewExtendedComponent);
  }
}
