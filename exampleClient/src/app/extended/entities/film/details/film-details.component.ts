import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { FilmExtendedService } from '../film.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { LanguageExtendedService } from 'src/app/extended/entities/language/language.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmDetailsComponent } from 'src/app/entities/film/index';

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.scss'],
})
export class FilmDetailsExtendedComponent extends FilmDetailsComponent implements OnInit {
  title: string = 'Film';
  parentUrl: string = 'film';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public filmExtendedService: FilmExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public languageExtendedService: LanguageExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      filmExtendedService,
      pickerDialogService,
      errorService,
      languageExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
