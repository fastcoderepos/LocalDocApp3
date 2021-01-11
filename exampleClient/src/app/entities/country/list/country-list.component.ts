import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ICountry } from '../icountry';
import { CountryService } from '../country.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CountryNewComponent } from '../new/country-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-country-list',
  templateUrl: './country-list.component.html',
  styleUrls: ['./country-list.component.scss'],
})
export class CountryListComponent extends BaseListComponent<ICountry> implements OnInit {
  title = 'Country';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public countryService: CountryService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, countryService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Country';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['countryId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [];
  }

  setColumns() {
    this.columns = [
      {
        column: 'country',
        searchColumn: 'country',
        label: 'country',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'countryId',
        searchColumn: 'countryId',
        label: 'country Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'lastUpdate',
        searchColumn: 'lastUpdate',
        label: 'last Update',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'actions',
        label: 'Actions',
        sort: false,
        filter: false,
        type: listColumnType.String,
      },
    ];
    this.selectedColumns = this.columns;
    this.displayedColumns = this.columns.map((obj) => {
      return obj.column;
    });
  }
  addNew(comp) {
    if (!comp) {
      comp = CountryNewComponent;
    }
    super.addNew(comp);
  }
}
