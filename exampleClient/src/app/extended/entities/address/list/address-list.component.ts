import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { AddressExtendedService } from '../address.service';
import { AddressNewExtendedComponent } from '../new/address-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CityExtendedService } from 'src/app/extended/entities/city/city.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { AddressListComponent } from 'src/app/entities/address/index';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.scss'],
})
export class AddressListExtendedComponent extends AddressListComponent implements OnInit {
  title: string = 'Address';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public addressService: AddressExtendedService,
    public errorService: ErrorService,
    public cityService: CityExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      addressService,
      errorService,
      cityService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(AddressNewExtendedComponent);
  }
}
