import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { CityExtendedService } from '../city.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CountryExtendedService } from 'src/app/extended/entities/country/country.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { CityDetailsComponent } from 'src/app/entities/city/index';

@Component({
  selector: 'app-city-details',
  templateUrl: './city-details.component.html',
  styleUrls: ['./city-details.component.scss'],
})
export class CityDetailsExtendedComponent extends CityDetailsComponent implements OnInit {
  title: string = 'City';
  parentUrl: string = 'city';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public cityExtendedService: CityExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public countryExtendedService: CountryExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      cityExtendedService,
      pickerDialogService,
      errorService,
      countryExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
