import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ICity } from '../icity';
import { CityService } from '../city.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CityNewComponent } from '../new/city-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CountryService } from 'src/app/entities/country/country.service';

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.scss'],
})
export class CityListComponent extends BaseListComponent<ICity> implements OnInit {
  title = 'City';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public cityService: CityService,
    public errorService: ErrorService,
    public countryService: CountryService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, cityService, errorService);
  }

  ngOnInit() {
    this.entityName = 'City';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['cityId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'countryId',
            value: undefined,
            referencedkey: 'countryId',
          },
        ],
        isParent: false,
        descriptiveField: 'countryDescriptiveField',
        referencedDescriptiveField: 'countryId',
        service: this.countryService,
        associatedObj: undefined,
        table: 'country',
        type: 'ManyToOne',
        url: 'citys',
        listColumn: 'country',
        label: 'country',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'city',
        searchColumn: 'city',
        label: 'city',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'cityId',
        searchColumn: 'cityId',
        label: 'city Id',
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
        column: 'countryDescriptiveField',
        searchColumn: 'country',
        label: 'country',
        sort: true,
        filter: true,
        type: listColumnType.String,
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
      comp = CityNewComponent;
    }
    super.addNew(comp);
  }
}
