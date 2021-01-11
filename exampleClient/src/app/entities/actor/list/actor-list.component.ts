import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IActor } from '../iactor';
import { ActorService } from '../actor.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ActorNewComponent } from '../new/actor-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-actor-list',
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.scss'],
})
export class ActorListComponent extends BaseListComponent<IActor> implements OnInit {
  title = 'Actor';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public actorService: ActorService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, actorService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Actor';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['actorId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [];
  }

  setColumns() {
    this.columns = [
      {
        column: 'actorId',
        searchColumn: 'actorId',
        label: 'actor Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'firstName',
        searchColumn: 'firstName',
        label: 'first Name',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'lastName',
        searchColumn: 'lastName',
        label: 'last Name',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'lastUpdate',
        searchColumn: 'lastUpdate',
        label: 'last Update',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
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
      comp = ActorNewComponent;
    }
    super.addNew(comp);
  }
}
