import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { FilmActorExtendedService } from '../film-actor.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { ActorExtendedService } from 'src/app/extended/entities/actor/actor.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { FilmActorDetailsComponent } from 'src/app/entities/film-actor/index';

@Component({
  selector: 'app-film-actor-details',
  templateUrl: './film-actor-details.component.html',
  styleUrls: ['./film-actor-details.component.scss'],
})
export class FilmActorDetailsExtendedComponent extends FilmActorDetailsComponent implements OnInit {
  title: string = 'FilmActor';
  parentUrl: string = 'filmactor';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public filmActorExtendedService: FilmActorExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public actorExtendedService: ActorExtendedService,
    public filmExtendedService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      filmActorExtendedService,
      pickerDialogService,
      errorService,
      actorExtendedService,
      filmExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
