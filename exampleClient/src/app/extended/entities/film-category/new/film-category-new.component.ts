import { Component, OnInit, Inject } from '@angular/core';
import { FilmCategoryExtendedService } from '../film-category.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CategoryExtendedService } from 'src/app/extended/entities/category/category.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { FilmCategoryNewComponent } from 'src/app/entities/film-category/index';

@Component({
  selector: 'app-film-category-new',
  templateUrl: './film-category-new.component.html',
  styleUrls: ['./film-category-new.component.scss'],
})
export class FilmCategoryNewExtendedComponent extends FilmCategoryNewComponent implements OnInit {
  title: string = 'New FilmCategory';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<FilmCategoryNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public filmCategoryExtendedService: FilmCategoryExtendedService,
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
      dialogRef,
      data,
      global,
      pickerDialogService,
      filmCategoryExtendedService,
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
