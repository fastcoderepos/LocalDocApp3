import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ILanguage } from '../ilanguage';
import { LanguageService } from '../language.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LanguageNewComponent } from '../new/language-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-language-list',
  templateUrl: './language-list.component.html',
  styleUrls: ['./language-list.component.scss'],
})
export class LanguageListComponent extends BaseListComponent<ILanguage> implements OnInit {
  title = 'Language';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public languageService: LanguageService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, languageService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Language';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['languageId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [];
  }

  setColumns() {
    this.columns = [
      {
        column: 'languageId',
        searchColumn: 'languageId',
        label: 'language Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
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
        column: 'name',
        searchColumn: 'name',
        label: 'name',
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
      comp = LanguageNewComponent;
    }
    super.addNew(comp);
  }
}
