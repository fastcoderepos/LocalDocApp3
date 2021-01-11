import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { CityExtendedService } from '../city.service';
import { CityNewExtendedComponent } from '../new/city-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CountryExtendedService } from 'src/app/extended/entities/country/country.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { CityListComponent } from 'src/app/entities/city/index';

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.scss'],
})
export class CityListExtendedComponent extends CityListComponent implements OnInit {
  title: string = 'City';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public cityService: CityExtendedService,
    public errorService: ErrorService,
    public countryService: CountryExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      cityService,
      errorService,
      countryService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(CityNewExtendedComponent);
  }
}
