import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { UserroleService } from '../userrole.service';
import { IUserrole } from '../iuserrole';
import { BaseDetailsComponent, Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { RoleService } from 'src/app/admin/user-management/role/role.service';
import { UserService } from 'src/app/admin/user-management/user/user.service';

@Component({
  selector: 'app-userrole-details',
  templateUrl: './userrole-details.component.html',
  styleUrls: ['./userrole-details.component.scss'],
})
export class UserroleDetailsComponent extends BaseDetailsComponent<IUserrole> implements OnInit {
  title = 'Userrole';
  parentUrl = 'userrole';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public userroleService: UserroleService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public roleService: RoleService,
    public userService: UserService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(formBuilder, router, route, dialog, global, pickerDialogService, userroleService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Userrole';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.getItem();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      roleId: ['', Validators.required],
      userId: ['', Validators.required],
      roleDescriptiveField: [''],
      userDescriptiveField: [''],
    });

    this.fields = [];
  }

  onItemFetched(item: IUserrole) {
    this.item = item;
    this.itemForm.patchValue(item);
  }

  setAssociations() {
    this.associations = [
      {
        column: [
          {
            key: 'roleId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        table: 'role',
        type: 'ManyToOne',
        label: 'role',
        service: this.roleService,
        descriptiveField: 'roleDescriptiveField',
        referencedDescriptiveField: 'displayName',
      },
      {
        column: [
          {
            key: 'userId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        table: 'user',
        type: 'ManyToOne',
        label: 'user',
        service: this.userService,
        descriptiveField: 'userDescriptiveField',
        referencedDescriptiveField: 'userName',
      },
    ];

    this.childAssociations = this.associations.filter((association) => {
      return association.isParent;
    });

    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let userrole = this.itemForm.getRawValue();
    super.onSubmit(userrole);
  }
}
