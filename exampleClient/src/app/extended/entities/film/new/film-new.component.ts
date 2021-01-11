import { Component, OnInit, Inject } from '@angular/core';
import { FilmExtendedService } from '../film.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { LanguageExtendedService } from 'src/app/extended/entities/language/language.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { FilmNewComponent } from 'src/app/entities/film/index';

@Component({
  selector: 'app-film-new',
  templateUrl: './film-new.component.html',
  styleUrls: ['./film-new.component.scss'],
})
export class FilmNewExtendedComponent extends FilmNewComponent implements OnInit {
  title: string = 'New Film';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<FilmNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public filmExtendedService: FilmExtendedService,
    public errorService: ErrorService,
    public languageExtendedService: LanguageExtendedService,
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
      filmExtendedService,
      errorService,
      languageExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
