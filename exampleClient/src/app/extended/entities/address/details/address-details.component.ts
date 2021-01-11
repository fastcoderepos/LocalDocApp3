import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { AddressExtendedService } from '../address.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CityExtendedService } from 'src/app/extended/entities/city/city.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { AddressDetailsComponent } from 'src/app/entities/address/index';

@Component({
  selector: 'app-address-details',
  templateUrl: './address-details.component.html',
  styleUrls: ['./address-details.component.scss'],
})
export class AddressDetailsExtendedComponent extends AddressDetailsComponent implements OnInit {
  title: string = 'Address';
  parentUrl: string = 'address';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public addressExtendedService: AddressExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public cityExtendedService: CityExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      addressExtendedService,
      pickerDialogService,
      errorService,
      cityExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
