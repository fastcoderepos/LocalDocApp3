import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { FilmExtendedService } from '../film.service';
import { FilmNewExtendedComponent } from '../new/film-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { LanguageExtendedService } from 'src/app/extended/entities/language/language.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmListComponent } from 'src/app/entities/film/index';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.scss'],
})
export class FilmListExtendedComponent extends FilmListComponent implements OnInit {
  title: string = 'Film';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmService: FilmExtendedService,
    public errorService: ErrorService,
    public languageService: LanguageExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      filmService,
      errorService,
      languageService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(FilmNewExtendedComponent);
  }
}
