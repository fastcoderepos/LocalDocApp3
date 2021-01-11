import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { UserroleService } from '../userrole.service';
import { IUserrole } from '../iuserrole';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { RoleService } from 'src/app/admin/user-management/role/role.service';
import { UserService } from 'src/app/admin/user-management/user/user.service';

@Component({
  selector: 'app-userrole-new',
  templateUrl: './userrole-new.component.html',
  styleUrls: ['./userrole-new.component.scss'],
})
export class UserroleNewComponent extends BaseNewComponent<IUserrole> implements OnInit {
  title: string = 'New Userrole';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<UserroleNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public userroleService: UserroleService,
    public errorService: ErrorService,
    public roleService: RoleService,
    public userService: UserService,
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
      userroleService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Userrole';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
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
        service: this.roleService,
        label: 'role',
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
        service: this.userService,
        label: 'user',
        descriptiveField: 'userDescriptiveField',
        referencedDescriptiveField: 'userName',
      },
    ];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let userrole = this.itemForm.getRawValue();
    super.onSubmit(userrole);
  }
}
