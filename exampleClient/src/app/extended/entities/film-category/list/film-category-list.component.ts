import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { FilmCategoryExtendedService } from '../film-category.service';
import { FilmCategoryNewExtendedComponent } from '../new/film-category-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CategoryExtendedService } from 'src/app/extended/entities/category/category.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmCategoryListComponent } from 'src/app/entities/film-category/index';

@Component({
  selector: 'app-film-category-list',
  templateUrl: './film-category-list.component.html',
  styleUrls: ['./film-category-list.component.scss'],
})
export class FilmCategoryListExtendedComponent extends FilmCategoryListComponent implements OnInit {
  title: string = 'FilmCategory';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmCategoryService: FilmCategoryExtendedService,
    public errorService: ErrorService,
    public categoryService: CategoryExtendedService,
    public filmService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      filmCategoryService,
      errorService,
      categoryService,
      filmService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(FilmCategoryNewExtendedComponent);
  }
}
