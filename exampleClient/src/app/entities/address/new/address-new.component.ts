import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { AddressService } from '../address.service';
import { IAddress } from '../iaddress';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CityService } from 'src/app/entities/city/city.service';

@Component({
  selector: 'app-address-new',
  templateUrl: './address-new.component.html',
  styleUrls: ['./address-new.component.scss'],
})
export class AddressNewComponent extends BaseNewComponent<IAddress> implements OnInit {
  title: string = 'New Address';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<AddressNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public addressService: AddressService,
    public errorService: ErrorService,
    public cityService: CityService,
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
      addressService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Address';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      address: ['', Validators.required],
      address2: [''],
      addressId: ['', Validators.required],
      district: ['', Validators.required],
      lastUpdate: ['', Validators.required],
      lastUpdateTime: ['12:00 AM', Validators.required],
      phone: ['', Validators.required],
      postalCode: [''],
      cityId: ['', Validators.required],
      cityDescriptiveField: ['', Validators.required],
    });

    this.fields = [
      {
        name: 'address',
        label: 'address',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'address2',
        label: 'address 2',
        isRequired: false,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'addressId',
        label: 'address Id',
        isRequired: true,
        isAutoGenerated: false,
        type: 'number',
      },
      {
        name: 'district',
        label: 'district',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'lastUpdate',
        label: 'last Update',
        isRequired: true,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'lastUpdateTime',
        label: 'last Update Time',
        isRequired: true,
        isAutoGenerated: false,
        type: 'time',
      },
      {
        name: 'phone',
        label: 'phone',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'postalCode',
        label: 'postal Code',
        isRequired: false,
        isAutoGenerated: false,
        type: 'string',
      },
    ];
  }

  setAssociations() {
    this.associations = [
      {
        column: [
          {
            key: 'cityId',
            value: undefined,
            referencedkey: 'cityId',
          },
        ],
        isParent: false,
        table: 'city',
        type: 'ManyToOne',
        service: this.cityService,
        label: 'city',
        descriptiveField: 'cityDescriptiveField',
        referencedDescriptiveField: 'cityId',
      },
    ];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let address = this.itemForm.getRawValue();
    address.lastUpdate = this.dataService.combineDateAndTime(address.lastUpdate, address.lastUpdateTime);
    delete address.lastUpdateTime;
    super.onSubmit(address);
  }
}
