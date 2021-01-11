import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IFilmCategory } from '../ifilm-category';
import { FilmCategoryService } from '../film-category.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilmCategoryNewComponent } from '../new/film-category-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CategoryService } from 'src/app/entities/category/category.service';
import { FilmService } from 'src/app/entities/film/film.service';

@Component({
  selector: 'app-film-category-list',
  templateUrl: './film-category-list.component.html',
  styleUrls: ['./film-category-list.component.scss'],
})
export class FilmCategoryListComponent extends BaseListComponent<IFilmCategory> implements OnInit {
  title = 'FilmCategory';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmCategoryService: FilmCategoryService,
    public errorService: ErrorService,
    public categoryService: CategoryService,
    public filmService: FilmService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, filmCategoryService, errorService);
  }

  ngOnInit() {
    this.entityName = 'FilmCategory';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['categoryId', 'filmId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'categoryId',
            value: undefined,
            referencedkey: 'categoryId',
          },
        ],
        isParent: false,
        descriptiveField: 'categoryDescriptiveField',
        referencedDescriptiveField: 'categoryId',
        service: this.categoryService,
        associatedObj: undefined,
        table: 'category',
        type: 'ManyToOne',
        url: 'filmCategorys',
        listColumn: 'category',
        label: 'category',
      },
      {
        column: [
          {
            key: 'filmId',
            value: undefined,
            referencedkey: 'filmId',
          },
        ],
        isParent: false,
        descriptiveField: 'filmDescriptiveField',
        referencedDescriptiveField: 'filmId',
        service: this.filmService,
        associatedObj: undefined,
        table: 'film',
        type: 'ManyToOne',
        url: 'filmCategorys',
        listColumn: 'film',
        label: 'film',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'lastUpdate',
        searchColumn: 'lastUpdate',
        label: 'last Update',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'categoryDescriptiveField',
        searchColumn: 'category',
        label: 'category',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'filmDescriptiveField',
        searchColumn: 'film',
        label: 'film',
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
      comp = FilmCategoryNewComponent;
    }
    super.addNew(comp);
  }
}
