import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { FilmActorExtendedService } from '../film-actor.service';
import { FilmActorNewExtendedComponent } from '../new/film-actor-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { ActorExtendedService } from 'src/app/extended/entities/actor/actor.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmActorListComponent } from 'src/app/entities/film-actor/index';

@Component({
  selector: 'app-film-actor-list',
  templateUrl: './film-actor-list.component.html',
  styleUrls: ['./film-actor-list.component.scss'],
})
export class FilmActorListExtendedComponent extends FilmActorListComponent implements OnInit {
  title: string = 'FilmActor';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmActorService: FilmActorExtendedService,
    public errorService: ErrorService,
    public actorService: ActorExtendedService,
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
      filmActorService,
      errorService,
      actorService,
      filmService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(FilmActorNewExtendedComponent);
  }
}
