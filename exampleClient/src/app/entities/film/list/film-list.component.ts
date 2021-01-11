import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IFilm } from '../ifilm';
import { FilmService } from '../film.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilmNewComponent } from '../new/film-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { LanguageService } from 'src/app/entities/language/language.service';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
})
export class FilmListComponent extends BaseListComponent<IFilm> implements OnInit {
  title = 'Film';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmService: FilmService,
    public errorService: ErrorService,
    public languageService: LanguageService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, filmService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Film';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['filmId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'languageId',
            value: undefined,
            referencedkey: 'languageId',
          },
        ],
        isParent: false,
        descriptiveField: 'languageDescriptiveField',
        referencedDescriptiveField: 'languageId',
        service: this.languageService,
        associatedObj: undefined,
        table: 'language',
        type: 'ManyToOne',
        url: 'films',
        listColumn: 'language',
        label: 'language',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'description',
        searchColumn: 'description',
        label: 'description',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'filmId',
        searchColumn: 'filmId',
        label: 'film Id',
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
        column: 'length',
        searchColumn: 'length',
        label: 'length',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'rating',
        searchColumn: 'rating',
        label: 'rating',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'releaseYear',
        searchColumn: 'releaseYear',
        label: 'release Year',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'rentalDuration',
        searchColumn: 'rentalDuration',
        label: 'rental Duration',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'rentalRate',
        searchColumn: 'rentalRate',
        label: 'rental Rate',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'replacementCost',
        searchColumn: 'replacementCost',
        label: 'replacement Cost',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'title',
        searchColumn: 'title',
        label: 'title',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'languageDescriptiveField',
        searchColumn: 'language',
        label: 'language',
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
      comp = FilmNewComponent;
    }
    super.addNew(comp);
  }
}
