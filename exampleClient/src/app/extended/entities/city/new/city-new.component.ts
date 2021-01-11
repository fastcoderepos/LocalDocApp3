import { Component, OnInit, Inject } from '@angular/core';
import { CityExtendedService } from '../city.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CountryExtendedService } from 'src/app/extended/entities/country/country.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CityNewComponent } from 'src/app/entities/city/index';

@Component({
  selector: 'app-city-new',
  templateUrl: './city-new.component.html',
  styleUrls: ['./city-new.component.scss'],
})
export class CityNewExtendedComponent extends CityNewComponent implements OnInit {
  title: string = 'New City';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<CityNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public cityExtendedService: CityExtendedService,
    public errorService: ErrorService,
    public countryExtendedService: CountryExtendedService,
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
      cityExtendedService,
      errorService,
      countryExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
