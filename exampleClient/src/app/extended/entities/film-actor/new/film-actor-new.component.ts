import { Component, OnInit, Inject } from '@angular/core';
import { FilmActorExtendedService } from '../film-actor.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ActorExtendedService } from 'src/app/extended/entities/actor/actor.service';
import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { FilmActorNewComponent } from 'src/app/entities/film-actor/index';

@Component({
  selector: 'app-film-actor-new',
  templateUrl: './film-actor-new.component.html',
  styleUrls: ['./film-actor-new.component.scss'],
})
export class FilmActorNewExtendedComponent extends FilmActorNewComponent implements OnInit {
  title: string = 'New FilmActor';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<FilmActorNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public filmActorExtendedService: FilmActorExtendedService,
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
      dialogRef,
      data,
      global,
      pickerDialogService,
      filmActorExtendedService,
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
