import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { FilmCategoryExtendedService } from '../film-category.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CategoryExtendedService } from 'src/app/extended/entities/category/category.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmCategoryDetailsComponent } from 'src/app/entities/film-category/index';

@Component({
  selector: 'app-film-category-details',
  templateUrl: './film-category-details.component.html',
  styleUrls: ['./film-category-details.component.scss'],
})
export class FilmCategoryDetailsExtendedComponent extends FilmCategoryDetailsComponent implements OnInit {
  title: string = 'FilmCategory';
  parentUrl: string = 'filmcategory';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public filmCategoryExtendedService: FilmCategoryExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public categoryExtendedService: CategoryExtendedService,
    public filmExtendedService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      filmCategoryExtendedService,
      pickerDialogService,
      errorService,
      categoryExtendedService,
      filmExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
