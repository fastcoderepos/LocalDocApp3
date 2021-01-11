import { Component, OnInit, Inject } from '@angular/core';
import { ActorExtendedService } from '../actor.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { ActorNewComponent } from 'src/app/entities/actor/index';

@Component({
  selector: 'app-actor-new',
  templateUrl: './actor-new.component.html',
  styleUrls: ['./actor-new.component.scss'],
})
export class ActorNewExtendedComponent extends ActorNewComponent implements OnInit {
  title: string = 'New Actor';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ActorNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public actorExtendedService: ActorExtendedService,
    public errorService: ErrorService,
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
      actorExtendedService,
      errorService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
