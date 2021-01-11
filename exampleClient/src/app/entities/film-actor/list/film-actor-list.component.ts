import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IFilmActor } from '../ifilm-actor';
import { FilmActorService } from '../film-actor.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilmActorNewComponent } from '../new/film-actor-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { ActorService } from 'src/app/entities/actor/actor.service';
import { FilmService } from 'src/app/entities/film/film.service';

@Component({
  selector: 'app-film-actor-list',
  templateUrl: './film-actor-list.component.html',
  styleUrls: ['./film-actor-list.component.scss'],
})
export class FilmActorListComponent extends BaseListComponent<IFilmActor> implements OnInit {
  title = 'FilmActor';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public filmActorService: FilmActorService,
    public errorService: ErrorService,
    public actorService: ActorService,
    public filmService: FilmService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, filmActorService, errorService);
  }

  ngOnInit() {
    this.entityName = 'FilmActor';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['actorId', 'filmId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'actorId',
            value: undefined,
            referencedkey: 'actorId',
          },
        ],
        isParent: false,
        descriptiveField: 'actorDescriptiveField',
        referencedDescriptiveField: 'actorId',
        service: this.actorService,
        associatedObj: undefined,
        table: 'actor',
        type: 'ManyToOne',
        url: 'filmActors',
        listColumn: 'actor',
        label: 'actor',
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
        url: 'filmActors',
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
        column: 'actorDescriptiveField',
        searchColumn: 'actor',
        label: 'actor',
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
      comp = FilmActorNewComponent;
    }
    super.addNew(comp);
  }
}
