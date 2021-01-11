import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { LanguageExtendedService } from '../language.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { LanguageDetailsComponent } from 'src/app/entities/language/index';

@Component({
  selector: 'app-language-details',
  templateUrl: './language-details.component.html',
  styleUrls: ['./language-details.component.scss'],
})
export class LanguageDetailsExtendedComponent extends LanguageDetailsComponent implements OnInit {
  title: string = 'Language';
  parentUrl: string = 'language';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public languageExtendedService: LanguageExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      languageExtendedService,
      pickerDialogService,
      errorService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
